package oauth;

import org.json.JSONException;
import typedef.AccountOperation;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface OAuthValidator {

    String validate(String token) throws JSONException;
}
