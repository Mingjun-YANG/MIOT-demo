
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oauth.OAuthValidator;
import oauth.impl.OAuthValidatorMockImpl;
import operater.*;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceHandler extends AbstractHandler {

    private JSONObject context;
    private OAuthValidator validator;

    private HeaderValidator headerValidator;
    private IntentSwitcher intentSwitcher;
    private String uid;

    ServiceHandler() {
        validator = new OAuthValidatorMockImpl();
        headerValidator = new HeaderValidator();
        intentSwitcher = new IntentSwitcher();
    }

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String token = request.getHeader("User_Token");
        try {
            uid = validator.validate(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        String requestId = context.optString("requestId");
        String intent = context.optString("intent");
        if (!headerValidator.headerValidator(response, requestId, intent)) {
            return;
        }
        if (intent.equals("get-devices")) {
            JSONObject obj = intentSwitcher.intentSwithcer(response, requestId, intent, uid);
            response.setStatus(200);
            out.println(obj);
        } else {
            try {

                List<JSONObject> listReturn = intentSwitcher.intentSwithcer(response, requestId, intent, context, uid);
                response.setStatus(200);
                out.println(listReturn);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        baseRequest.setHandled(true);
    }
}
