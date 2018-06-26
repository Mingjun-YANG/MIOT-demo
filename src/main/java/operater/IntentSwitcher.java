package operater;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class IntentSwitcher {


    public JSONObject intentSwithcer(HttpServletResponse response, String requestId, String intent, String uid) throws IOException {
        JSONObject objreturn = new JSONObject();
        IntentOperater intentOperater = new IntentOperater();
        if (intent.equals("get-devices")) {
            try {
                objreturn = intentOperater.onGetDevices(response, requestId, intent, uid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return objreturn;
    }

    public List<JSONObject> intentSwithcer(HttpServletResponse response, String requestId, String intent, JSONObject context, String uid) throws IOException, JSONException {
        IntentOperater intentOperater = new IntentOperater();
        switch (intent.hashCode()) {
            case -1687278293:
                if (intent.equals("invoke-action")) {
                    return intentOperater.onExcecuteAction(response, requestId, intent, uid, context);
                }
                break;
            case 30608254:
                if (intent.equals("set-properties")) {
                    return intentOperater.onSetProperties(response, requestId, intent, uid, context);
                }
                break;
            case 1802344458:
                if (intent.equals("get-properties")) {
                    return intentOperater.onGetProperties(response, requestId, intent, uid, context);
                }
                break;
            case 514841930:
                if (intent.equals("subscribe")) {
                    return intentOperater.onSubscribe(response, requestId, intent, uid, context);
                }
                break;
            case 583281361:
                if (intent.equals("unsubscribe")) {
                    return intentOperater.onSubscribe(response, requestId, intent, uid, context);
                }
                break;
            case 1336926482:
                if (intent.equals("get-device-status")) {
                    return intentOperater.onGetStatus(response, requestId, intent, uid, context);
                }
                break;
            default:
                response.setStatus(400);
                PrintWriter out = response.getWriter();
                out.println("intent is invalid");
                break;
        }
        return null;
    }
}
