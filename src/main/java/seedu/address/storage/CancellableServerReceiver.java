package seedu.address.storage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.repackaged.com.google.common.base.Throwables;

//@@author Caijun7-reused
/**
 * A cancellable Server Receiver.
 */
class CancellableServerReceiver implements VerificationCodeReceiver {

    private static final String LOCALHOST = "localhost";
    private static final String CALLBACK_PATH = "/Callback";

    private Server server;
    private String code;
    private String error;
    private final Lock lock = new ReentrantLock();
    private final Condition gotAuthorizationResponse = lock.newCondition();
    private int port;
    private final String host;
    private final String callbackPath;
    private String successLandingPageUrl;
    private String failureLandingPageUrl;

    /**
     * Constructor that starts the server on {@link #LOCALHOST} and an unused port.
     */
    public CancellableServerReceiver() {
        this(LOCALHOST, -1, CALLBACK_PATH, null, null);
    }

    /**
     * Constructor.
     *
     * @param host Host name to use
     * @param port Port to use or {@code -1} to select an unused port
     */
    CancellableServerReceiver(String host, int port, String callbackPath,
                              String successLandingPageUrl, String failureLandingPageUrl) {
        this.host = host;
        this.port = port;
        this.callbackPath = callbackPath;
        this.successLandingPageUrl = successLandingPageUrl;
        this.failureLandingPageUrl = failureLandingPageUrl;
    }

    @Override
    public String getRedirectUri() throws IOException {
        if (port == -1) {
            port = getUnusedPort();
        }
        server = new Server(port);
        for (Connector c : server.getConnectors()) {
            c.setHost(host);
        }
        server.addHandler(new CallbackHandler());
        try {
            server.start();
        } catch (Exception ex) {
            Throwables.propagateIfPossible(ex);
            throw new IOException(ex);
        }
        return "http://" + host + ":" + port + CALLBACK_PATH;
    }

    /**
     * Locks the thread and wait for the authorization code
     *
     * @throws IOException      When user decline access
     * @throws RuntimeException When authorization request timed out after 10 seconds
     */
    @Override
    public String waitForCode() throws IOException {
        lock.lock();
        try {
            long startTime = System.currentTimeMillis();
            while (code == null && error == null) {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed > 10000) {
                    throw new RuntimeException("Request timeout (" + error + ")");
                }
                gotAuthorizationResponse.await(10, TimeUnit.SECONDS);
            }
            if (error != null) {
                throw new IOException("User authorization failed (" + error + ")");
            }
            return code;
        } catch (InterruptedException e) {
            throw new RuntimeException("Request timeout (" + error + ")");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() throws IOException {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception ex) {
                Throwables.propagateIfPossible(ex);
                throw new IOException(ex);
            }
            lock.lock();
            try {
                code = null;
                gotAuthorizationResponse.signal();
            } finally {
                lock.unlock();
            }
            server = null;
        }
    }

    private static int getUnusedPort() throws IOException {
        Socket socket = new Socket();
        socket.bind(null);
        try {
            return socket.getLocalPort();
        } finally {
            socket.close();
        }
    }

    /**
     * Jetty handler that takes the verifier token passed over from the OAuth provider and stashes it
     * where {@link #waitForCode} will find it.
     */
    class CallbackHandler extends AbstractHandler {

        @Override
        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
                throws IOException {
            if (!CALLBACK_PATH.equals(target)) {
                return;
            }
            Request requestWrapper = (Request) request;
            requestWrapper.setHandled(true);
            lock.lock();
            try {
                error = request.getParameter("error");
                code = request.getParameter("code");
                gotAuthorizationResponse.signal();
            } finally {
                lock.unlock();
            }

            if (error == null && successLandingPageUrl != null) {
                response.sendRedirect(successLandingPageUrl);
            } else if (error != null && failureLandingPageUrl != null) {
                response.sendRedirect(failureLandingPageUrl);
            } else {
                writeLandingHtml(response);
            }
            response.flushBuffer();
        }

        /**
         * Produces the landing html page after user accept the authorization request
         *
         * @param response
         * @throws IOException
         */
        private void writeLandingHtml(HttpServletResponse response) throws IOException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");

            PrintWriter doc = response.getWriter();
            doc.println("<html>");
            doc.println("<head><title>OAuth 2.0 Authentication Token Received</title></head>");
            doc.println("<body>");
            doc.println("Received verification code. You may now close this window.");
            doc.println("</body>");
            doc.println("</html>");
            doc.flush();
        }
    }
}
