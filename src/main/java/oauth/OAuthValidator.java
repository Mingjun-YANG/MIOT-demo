package oauth;

import org.json.JSONException;
public interface OAuthValidator {

    String validate(String token) throws JSONException;
}
