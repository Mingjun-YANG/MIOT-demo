import db.impl.DeviceDBLocalJsonImpl;
import miot.MiotRequest;
import miot.MiotRequestCodec;
import miot.Response;
import miot.impl.RequestImpl;
import miot.impl.ResponseImpl;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;
import validator.impl.LocalDBValidatorImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServiceHandler extends AbstractHandler {

    private DeviceDBLocalJsonImpl deviceDB = new DeviceDBLocalJsonImpl();
    private LocalDBValidatorImpl validator = new LocalDBValidatorImpl();
    private Response responseFiller = new ResponseImpl();
    private RequestImpl request = new RequestImpl();

    private JSONObject context;


    private void onGetDevices(MiotRequest req) throws JSONException {
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
    }

    private void onGetProperties(MiotRequest req, JSONObject context) {
        List<PropertyRequest> list = request.getPropertyRequest(context);
        JSONArray propertiesArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return;
        }

        list.forEach(PropertyRequest -> {
            if (validator.deviceRequestValidator(PropertyRequest.getDid(), uid)) {
                try {
                    propertiesArray.put(responseFiller.deviceNotFoundResponse(PropertyRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            try {
                Instance instance = deviceDB.getInstance(PropertyRequest.getDid());
                if (!validator.serviceRequestValidator(instance, PropertyRequest.getSiid())) {
                    try {
                        propertiesArray.put(responseFiller.serviceNotFoundResponse(PropertyRequest));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                List<Services> services = deviceDB.getServices(instance);
                services.forEach(Services -> {


                    if (Services.getSiid() == (PropertyRequest.getSiid())) {

                        if (!validator.propertyRequestValidator(Services, PropertyRequest.getPiid())) {
                            try {
                                propertiesArray.put(responseFiller.propertyNotFoundResponse(PropertyRequest));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return;
                        }

                        try {
                            List<Properties> properties = deviceDB.getProperties(Services);
                            properties.forEach(Properties -> {

                                if (Properties.getIid() == (PropertyRequest.getPiid())) {
                                    try {
                                        propertiesArray.put(responseFiller.propertyGetResponse(PropertyRequest, Properties));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    private void onSetProperties(MiotRequest req, JSONObject context) {

        List<PropertyRequest> list = request.setPropertyRequest(context);

        JSONArray propertiesArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return;
        }

        list.forEach(PropertyRequest -> {
            if (validator.deviceRequestValidator(PropertyRequest.getDid(), uid)) {
                try {
                    propertiesArray.put(responseFiller.deviceNotFoundResponse(PropertyRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            try {
                Instance instance = deviceDB.getInstance(PropertyRequest.getDid());
                if (!validator.serviceRequestValidator(instance, PropertyRequest.getSiid())) {
                    try {
                        propertiesArray.put(responseFiller.serviceNotFoundResponse(PropertyRequest));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                List<Services> services = deviceDB.getServices(instance);
                services.forEach(Services -> {


                    if (Services.getSiid() == (PropertyRequest.getSiid())) {

                        if (!validator.propertyRequestValidator(Services, PropertyRequest.getPiid())) {
                            try {
                                propertiesArray.put(responseFiller.propertyNotFoundResponse(PropertyRequest));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return;
                        }

                        try {
                            List<Properties> properties = deviceDB.getProperties(Services);
                            properties.forEach(Properties -> {

                                if (Properties.getIid() == (PropertyRequest.getPiid())) {
                                    if (!validator.valueFormatValidator(Properties.getFormat(), PropertyRequest.getValue())) {
                                        try {
                                            propertiesArray.put(responseFiller.propertyInvalidFormatResponse(PropertyRequest));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return;
                                    }
                                    if (PropertyRequest.getValue() instanceof Float || PropertyRequest.getValue() instanceof Integer) {
                                        try {
                                            if (!validator.valueRangeValidator(Properties.getValue_range(), PropertyRequest.getValue(), Properties.getFormat())) {
                                                try {
                                                    propertiesArray.put(responseFiller.propertyValueOutRangeResponse(PropertyRequest));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                return;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (!validator.writeAccessValidator(Properties.getAccess())) {
                                        try {
                                            propertiesArray.put(responseFiller.propertyNotWriteableResponse(PropertyRequest));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return;
                                    }
                                    try {
                                        propertiesArray.put(responseFiller.propertySetResponse(PropertyRequest));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    private void onExcecuteActions(MiotRequest req, JSONObject context) {

        List<ActionRequest> list = request.excecuteActionRequest(context);

        JSONArray actionsArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return;
        }

        list.forEach(ActionRequest -> {
            if (validator.deviceRequestValidator(ActionRequest.getDid(), uid)) {
                try {
                    actionsArray.put(responseFiller.deviceNotFoundResponse(ActionRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            try {
                Instance instance = deviceDB.getInstance(ActionRequest.getDid());
                if (!validator.serviceRequestValidator(instance, ActionRequest.getSiid())) {
                    try {
                        actionsArray.put(responseFiller.serviceNotFoundResponse(ActionRequest));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                List<Services> services = deviceDB.getServices(instance);
                services.forEach(Services -> {

                    if (Services.getSiid() == (ActionRequest.getSiid())) {

                        if (!validator.actionRequestValidator(Services, ActionRequest.getAiid())) {
                            try {
                                actionsArray.put(responseFiller.actionNotFoundResponse(ActionRequest));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return;
                        }

                        try {
                            List<Actions> actions = deviceDB.getActions(Services);
                            actions.forEach(Actions -> {

                                if (Actions.getIid() == (ActionRequest.getAiid())) {
                                    try {
                                        actionsArray.put(responseFiller.actionExcecutedResponse(ActionRequest, Actions));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    private void onSubscribe(MiotRequest req, JSONObject context) {

        List<SubscribeRequest> list = request.subscribeRequest(context);

        JSONArray subscribeArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return;
        }

        list.forEach(SubscribeRequest -> {
            if (validator.deviceRequestValidator(SubscribeRequest.getDid(), uid)) {
                try {
                    subscribeArray.put(responseFiller.deviceNotFoundResponse(SubscribeRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            try {
                Instance instance = deviceDB.getInstance(SubscribeRequest.getDid());
                if (validator.subscribeRequestValidator(instance, SubscribeRequest.getSubscriptionId())) {
                    try {
                        subscribeArray.put(responseFiller.subscribeIdInvalidResponse(SubscribeRequest));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                try {
                    subscribeArray.put(responseFiller.subscribeExcecutedResponse(SubscribeRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    private void onGetStatus(MiotRequest req, JSONObject context) {

        JSONArray devices = context.optJSONArray("devices");

        JSONArray deviceStatusArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());
        if (uid.equals("NOTFOUND")) {
            return;
        }

        for (int i = 0; i < devices.length(); i++) {
            String did = devices.optString(i);
            if (validator.deviceRequestValidator(did, uid)) {
                try {
                    deviceStatusArray.put(responseFiller.deviceNotFoundResponse(did));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }
            try {
                Instance instance = deviceDB.getInstance(did);
                try {
                    deviceStatusArray.put(responseFiller.getStatusResponse(did, instance.getStatus(), instance.getType()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        context = MiotRequestCodec.bodyReader(request);
        MiotRequest req = MiotRequestCodec.decode(request, context);
        switch (req.getIntent()) {
            case GET_DEVICES:
                try {
                    onGetDevices(req);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case GET_PROPERTIES:
                onGetProperties(req, context);
                break;
            case SET_PROPERTIES:
                onSetProperties(req, context);
            case SUBSCRIBE:
            case UNSUBSCRIBE:
                onSubscribe(req, context);
            case INVOKE_ACTION:
                onExcecuteActions(req, context);
            case GET_DEVICE_STATUS:
                onGetStatus(req, context);
            default:
                break;
        }

    }

}
