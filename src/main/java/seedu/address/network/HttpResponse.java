package seedu.address.network;

import org.asynchttpclient.Response;

/**
 * A wrapper around the Response class from async-http-client.
 */
public class HttpResponse {

    private final int statusCode;
    private final String contentType;
    private final String responseBody;

    public HttpResponse(int statusCode, String contentType, String responseBody) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.responseBody = responseBody;
    }

    public HttpResponse(Response response) {
        this(response.getStatusCode(), response.getContentType(), response.getResponseBody());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
