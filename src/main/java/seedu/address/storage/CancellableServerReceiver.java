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

    /**
     * Server or {@code null} before {@link #getRedirectUri()}.
     */
    private Server server;

    /**
     * Verification code or {@code null} for none.
     */
    private String code;

    /**
     * Error code or {@code null} for none.
     */
    private String error;

    /**
     * Lock on the code and error.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Condition for receiving an authorization response.
     */
    private final Condition gotAuthorizationResponse = lock.newCondition();

    /**
     * Port to use or {@code -1} to select an unused port in {@link #getRedirectUri()}.
     */
    private int port;

    /**
     * Host name to use.
     */
    private final String host;

    /**
     * Callback path of redirect_uri
     */
    private final String callbackPath;

    /**
     * URL to an HTML page to be shown (via redirect) after successful login. If null, a canned
     * default landing page will be shown (via direct response).
     */
    private String successLandingPageUrl;

    /**
     * URL to an HTML page to be shown (via redirect) after failed login. If null, a canned
     * default landing page will be shown (via direct response).
     */
    private String failureLandingPageUrl;

    /**
     * Constructor that starts the server on {@link #LOCALHOST} and an unused port.
     * Use {@link Builder} if you need to specify any of the optional parameters.
     */
    public CancellableServerReceiver() {
        this(LOCALHOST, -1, CALLBACK_PATH, null, null);
    }

    /**
     * Constructor that starts the server on {@code "localhost"} selects an unused port.
     * Use {@link Builder} if you need to specify any of the optional parameters.
     */
    public CancellableServerReceiver(String host, int port,
                                     String successLandingPageUrl, String failureLandingPageUrl) {
        this(host, port, CALLBACK_PATH, successLandingPageUrl, failureLandingPageUrl);
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

    /**
     * Returns the host name to use.
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the port to use or {@code -1} to select an unused port in {@link #getRedirectUri()}.
     */
    public int getPort() {
        return port;
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
     * Builder class for CancellableServerReceiver. Implementation is not thread-safe.
     */
    public static final class Builder {

        /**
         * Host name to use.
         */
        private String host = LOCALHOST;

        /**
         * Port to use or {@code -1} to select an unused port.
         */
        private int port = -1;

        private String successLandingPageUrl;
        private String failureLandingPageUrl;

        private String callbackPath = CALLBACK_PATH;

        /**
         * Builds the {@code CancellableServerReceiver}.
         */
        public CancellableServerReceiver build() {
            return new CancellableServerReceiver(host, port, callbackPath,
                    successLandingPageUrl, failureLandingPageUrl);
        }

        public String getHost() {
            return host;
        }

        /**
         * Sets the host name to use.
         */
        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * Returns the port to use or {@code -1} to select an unused port.
         */
        public int getPort() {
            return port;
        }

        /**
         * Sets the port to use or {@code -1} to select an unused port.
         */
        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        /**
         * Returns the callback path of redirect_uri
         */
        public String getCallbackPath() {
            return callbackPath;
        }

        /**
         * Sets the callback path of redirect_uri
         */
        public Builder setCallbackPath(String callbackPath) {
            this.callbackPath = callbackPath;
            return this;
        }

        public Builder setLandingPages(String successLandingPageUrl, String failureLandingPageUrl) {
            this.successLandingPageUrl = successLandingPageUrl;
            this.failureLandingPageUrl = failureLandingPageUrl;
            return this;
        }
    }

    /**
     * Jetty handler that takes the verifier token passed over from the OAuth provider and stashes it
     * where {@link #waitForCode} will find it.
     */
    class CallbackHandler extends AbstractHandler {

        @Override
        public void handle(String target, HttpServletRequest request, HttpServletResponse response,
                           int dispatch) throws IOException {
            if (!CALLBACK_PATH.equals(target)) {
                return;
            }

            ((Request) request).setHandled(true);
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
