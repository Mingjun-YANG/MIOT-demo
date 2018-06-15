
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oauth.OAuthValidator;
import oauth.impl.OAuthValidatorMockImpl;
import operater.*;
import status.Status;
import status.impl.DeviceStatusMockImpl;
import subscribe.Subscribe;
import subscribe.impl.SubscribeMockImpl;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceHandler extends AbstractHandler {

    private JSONObject context;
    private JSONArray array;
    private String requestId;
    private String intent;
    private OAuthValidator validator;
    private Subscribe subscribe;
    private DecodeOperater decodeOperater;
    private HeaderValidator headerValidator;
    private IntentSwitcher intentSwitcher;
    private Status deviceStatus;
    private String uid;

    public ServiceHandler() {
        validator = new OAuthValidatorMockImpl();
        subscribe = new SubscribeMockImpl();
        deviceStatus = new DeviceStatusMockImpl();
        decodeOperater = new DecodeOperater();
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
        //To JSON
        try {
            context = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            requestId = context.optString("requestId");
            intent = context.optString("intent");
//            if (intent != "get-devices") {
//                context.optJSONObject(body);
//            } else {
//                context.optJSONObject(null);
//            }

        if (!headerValidator.headerValidator(response, requestId, intent)) {
            return;
        }
        if (intent == "get-devices") {
            JSONObject obj = intentSwitcher.intentSwithcer(response, requestId, intent, uid);
            response.setStatus(200);
            out.println(obj);
        } else {
            try {
                JSONObject obj = intentSwitcher.intentSwithcer(response, requestId,intent, context, uid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        baseRequest.setHandled(true);
    }
}
