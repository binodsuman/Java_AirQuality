import java.util.List;
import java.util.Map;

public class HttpResponse {

    Map<String, List<String>> headers;
    int responseCode;
    String body;
    String responseMessage;

    public HttpResponse(String body, Map<String, List<String>> headers, int responseCode) {
        this.body = body;
        this.headers = headers;
        this.responseCode = responseCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getBody() {
        return body;
    }
}