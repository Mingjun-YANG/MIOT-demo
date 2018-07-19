package miot;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class MiotRequestCodec {


    public static MiotRequest decode(HttpServletRequest request, JSONObject context) {
        MiotRequest miotRequest = new MiotRequest();

        miotRequest.setIntent(MiotIntent.from(context.optString("intent")));
        miotRequest.setRequestId(context.optString("requestId"));
        miotRequest.setToken(request.getHeader("User_Token"));

        return miotRequest;
    }

    public static JSONObject bodyReader(HttpServletRequest request) throws IOException {
        JSONObject context = new JSONObject();
        BufferedReader reader = request.getReader();
        StringBuilder buff1 = new StringBuilder();
        String st;
        while ((st = reader.readLine()) != null) {
            buff1.append(st);
        }
        String body = buff1.toString();
        try {
            context = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return context;
    }
}
