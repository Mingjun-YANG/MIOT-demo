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
//    private Account account1;
//
//    public OAuthValidatorMockImpl() {
//        account1 = new AccountMockImpl();
//    }
    @Override
    public String validate(String token) throws JSONException{
        String uid = new String();
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
        for (int i = 0; i < list.size(); i++) {
        if (list.get(i).token.equals(token)) {
            uid = list.get(i).uid;
            break;
        }
        }
        return uid;
    }
}