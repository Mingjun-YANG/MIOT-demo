package typedef;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountOperation extends AbstractOperation {
    public String token;

    // 功能ID
    public String uid;

    public static AccountOperation decodeGetAccount(JSONObject o) {
        AccountOperation property = new AccountOperation();
        try {
            property.token = o.getString("token");
            property.uid = o.getString("uid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return property;
    }
}
