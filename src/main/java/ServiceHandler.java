import db.impl.DeviceDBLocalJsonImpl;
import miot.MiotRequest;
import miot.MiotRequestCodec;
import miot.Response;
import miot.impl.RequestImpl;
import miot.impl.ResponseImpl;
import operater.impl.RequestOperaterImpl;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.ActionRequest;
import typedef.Device;
import typedef.PropertyRequest;
import typedef.SubscribeRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ServiceHandler extends AbstractHandler {

    private DeviceDBLocalJsonImpl deviceDB = new DeviceDBLocalJsonImpl();
    private Response responseFiller = new ResponseImpl();
    private RequestImpl request = new RequestImpl();
    private RequestOperaterImpl requestOperater = new RequestOperaterImpl();


    private JSONObject onGetDevices(MiotRequest req) throws JSONException{
        String uid = deviceDB.getUid(req.getToken());
        List<Device> list = deviceDB.getDevices(uid);
        JSONArray deviceArray = new JSONArray();
        list.forEach(Device -> {

            JSONObject deviceObject = new JSONObject();
            try {
                deviceObject.put("type", Device.getType());
                deviceObject.put("did", Device.getDid());
                deviceArray.put(deviceObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return responseFiller.fillStatusResponse(deviceArray, req);
    }

    private JSONObject onGetProperties(MiotRequest req, JSONObject context) throws JSONException {
        List<PropertyRequest> list = request.getPropertyRequest(context);

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return responseFiller.tokenInvalidResponse(req);
        }

        return responseFiller.fillPropertyResponse(requestOperater.propertyRequestOperater(list, req), req);


    }

    private JSONObject onSetProperties(MiotRequest req, JSONObject context) throws JSONException {

        List<PropertyRequest> list = request.setPropertyRequest(context);

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return responseFiller.tokenInvalidResponse(req);
        }

        return responseFiller.fillPropertyResponse(requestOperater.propertyRequestOperater(list, req), req);

    }

    private JSONObject onExcecuteActions(MiotRequest req, JSONObject context) throws JSONException {

        List<ActionRequest> list = request.excecuteActionRequest(context);

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return responseFiller.tokenInvalidResponse(req);
        }

        return responseFiller.fillActionResponse(requestOperater.actionRequestOperater(list, req), req);

    }

    private JSONObject onSubscribe(MiotRequest req, JSONObject context) throws JSONException {

        List<SubscribeRequest> list = request.subscribeRequest(context);

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return responseFiller.tokenInvalidResponse(req);
        }

        return responseFiller.fillSubscribeResponse(requestOperater.subscribeRequestOperater(list, req), req);

    }

    private JSONObject onGetStatus(MiotRequest req, JSONObject context) throws JSONException {

        JSONArray devices = context.optJSONArray("devices");

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return responseFiller.tokenInvalidResponse(req);
        }

        return responseFiller.fillStatusResponse(requestOperater.deviceStatusRequestOperater(devices, req), req);
    }

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        JSONObject context = MiotRequestCodec.bodyReader(request);
        MiotRequest req = MiotRequestCodec.decode(request, context);
        response.setStatus(200);
        switch (req.getIntent()) {
            case GET_DEVICES:
                try {
                    out.println(onGetDevices(req));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case GET_PROPERTIES:
                try {
                    out.println(onGetProperties(req, context));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case SET_PROPERTIES:
                try {
                    out.println(onSetProperties(req, context));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case SUBSCRIBE:
            case UNSUBSCRIBE:
                try {
                    out.println(onSubscribe(req, context));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case INVOKE_ACTION:
                try {
                    out.println(onExcecuteActions(req, context));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case GET_DEVICE_STATUS:
                try {
                    out.println(onGetStatus(req, context));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        baseRequest.setHandled(true);
    }

}
