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
        switch (intent.hashCode()) {
//            case -1687278293:
//                if (intent.equals("invoke-action")) {
//                    return intentOperater.onExecuteAction(context);
//                }
//                break;
            case 30608254:
                if (intent.equals("set-properties")) {
                    IntentOperater intentOperater = new IntentOperater();
                    return intentOperater.onSetProperties(response, requestId, intent, uid, context);
                }
                break;
//            case 395143526:
//                if (intent.equals("get-devices")) {
//                    try {
//                        return intentOperater.onGetDevices(response, intent, context, uid);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
            case 1802344458:
                if (intent.equals("get-properties")) {
                    IntentOperater intentOperater = new IntentOperater();
                    return intentOperater.onGetProperties(response, requestId, intent, uid, context);
                }
                break;
//            case 514841930:
//                if (intent.equals("subscribe")) {
//                    return intentOperater.onSubscribe(context);
//                }
//                break;
//            case 583281361:
//                if (intent.equals("unsubscribe")) {
//                    return intentOperater.offSubscribe(context);
//                }
//                break;
//            case 1336926482:
//                if (intent.equals("get-device-status")) {
//                    return intentOperater.getDeviceStatus(context);
//                }
//                break;
//            default:
//                response.setStatus(400);
//                PrintWriter out = response.getWriter();
//                out.println("intent is invalid");
//                break;
//        }

        }
        return null;
    }
}
