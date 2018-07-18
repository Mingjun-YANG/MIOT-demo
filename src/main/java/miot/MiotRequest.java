package miot;


public class MiotRequest {

    private String token;

    private String requestId;

    private MiotIntent intent;


    public String getToken() {
        return token;
    }

    public String getRequestId() {
        return requestId;
    }

    public MiotIntent getIntent() {
        return intent;
    }

    public void setIntent(MiotIntent intent) {
        this.intent = intent;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


}
