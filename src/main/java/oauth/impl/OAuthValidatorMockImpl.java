package oauth.impl;

import oauth.OAuthValidator;
import operater.DatabaseOperater;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.AccountOperation;

import java.util.ArrayList;
import java.util.List;

public class OAuthValidatorMockImpl implements OAuthValidator {

    @Override
    public String validate(String token) throws JSONException{
        String uid = "";
        DatabaseOperater reader = new DatabaseOperater();
        String string = reader.databaseReader("account");
        JSONObject json = new JSONObject(string);
        JSONArray array = json.getJSONArray("db");

        List<AccountOperation> list = new ArrayList<>();
        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject aaa = array.optJSONObject(i);
                AccountOperation bbb = AccountOperation.decodeGetAccount(aaa);
                list.add(bbb);
            }
        }
        for (AccountOperation aList : list) {
            if (aList.token.equals(token)) {
                uid = aList.uid;
                break;
            }
        }
        return uid;
    }
}