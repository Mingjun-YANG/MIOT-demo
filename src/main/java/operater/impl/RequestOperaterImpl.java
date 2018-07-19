package operater.impl;

import db.impl.DeviceDBLocalJsonImpl;
import miot.MiotRequest;
import miot.Response;
import miot.impl.ResponseImpl;
import operater.RequestOperater;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;
import validator.impl.LocalDBValidatorImpl;

import java.util.List;

public class RequestOperaterImpl implements RequestOperater {

    private DeviceDBLocalJsonImpl deviceDB = new DeviceDBLocalJsonImpl();
    private LocalDBValidatorImpl validator = new LocalDBValidatorImpl();
    private Response responseFiller = new ResponseImpl();


    @Override
    public JSONArray actionRequestOperater(List<ActionRequest> list, MiotRequest req) {
        JSONArray actionsArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());

        list.forEach(ActionRequest -> {
            if (validator.deviceRequestValidator(ActionRequest.getDid(), uid)) {
                try {
                    actionsArray.put(responseFiller.deviceNotFoundResponse(ActionRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

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
                }
            });
        });

        return actionsArray;
    }

    @Override
    public JSONArray subscribeRequestOperater(List<SubscribeRequest> list, MiotRequest req) {

        JSONArray subscribeArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());

        list.forEach(SubscribeRequest -> {
            if (validator.deviceRequestValidator(SubscribeRequest.getDid(), uid)) {
                try {
                    subscribeArray.put(responseFiller.deviceNotFoundResponse(SubscribeRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

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
        });

        return subscribeArray;
    }

    public JSONArray deviceStatusRequestOperater(JSONArray devices, MiotRequest req) {
        JSONArray deviceStatusArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());
        for (int i = 0; i < devices.length(); i++) {
            String did = devices.optString(i);
            if (validator.deviceRequestValidator(did, uid)) {
                try {
                    deviceStatusArray.put(responseFiller.deviceNotFoundResponse(did));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            Instance instance = deviceDB.getInstance(did);
            try {
                deviceStatusArray.put(responseFiller.getStatusResponse(did, instance.getStatus(), instance.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return deviceStatusArray;
    }

    public JSONArray propertyRequestOperater(List<PropertyRequest> list, MiotRequest req) {
        JSONArray propertiesArray = new JSONArray();

        String uid = deviceDB.getUid(req.getToken());

        for (typedef.PropertyRequest PropertyRequest : list) {
            if (validator.deviceRequestValidator(PropertyRequest.getDid(), uid)) {
                try {
                    propertiesArray.put(responseFiller.deviceNotFoundResponse(PropertyRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                continue;
            }

            Instance instance = deviceDB.getInstance(PropertyRequest.getDid());
            if (!validator.serviceRequestValidator(instance, PropertyRequest.getSiid())) {
                try {
                    propertiesArray.put(responseFiller.serviceNotFoundResponse(PropertyRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                continue;
            }
            List<Services> services = deviceDB.getServices(instance);
            for (typedef.Services Services : services) {
                if (Services.getSiid() == (PropertyRequest.getSiid())) {

                    if (!validator.propertyRequestValidator(Services, PropertyRequest.getPiid())) {
                        try {
                            propertiesArray.put(responseFiller.propertyNotFoundResponse(PropertyRequest));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    List<Properties> properties = deviceDB.getProperties(Services);
                    properties.forEach(Properties -> {
                        if (Properties.getIid() == (PropertyRequest.getPiid())) {
                            switch (req.getIntent()) {
                                case GET_PROPERTIES:
                                    propertiesArray.put(getPropertyRequestOperater(Properties, PropertyRequest));
                                    break;
                                case SET_PROPERTIES:
                                    propertiesArray.put(setPropertyRequestOperater(Properties, PropertyRequest));
                                    break;
                            }
                        }
                    });
                }
            }
        }
        return propertiesArray;
    }


    private JSONObject getPropertyRequestOperater(Properties properties, PropertyRequest propertyRequest) {
        JSONObject propertyObject = new JSONObject();
        if (!validator.readAccessValidator(properties.getAccess())) {
            try {
                propertyObject = responseFiller.propertyNotReadableResponse(propertyRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return propertyObject;
        }
        try {
            propertyObject = (responseFiller.propertyGetResponse(propertyRequest, properties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertyObject;
    }

    private JSONObject setPropertyRequestOperater(Properties properties, PropertyRequest propertyRequest) {
        JSONObject propertyObject = new JSONObject();
        if (!validator.valueFormatValidator(properties.getFormat(), properties.getValue())) {
            try {
                propertyObject = responseFiller.propertyInvalidFormatResponse(propertyRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return propertyObject;
        }
        if (propertyRequest.getValue() instanceof Float || propertyRequest.getValue() instanceof Integer) {
            if (!validator.valueRangeValidator(properties.getValue_range(), propertyRequest.getValue(), properties.getFormat())) {
                try {
                    propertyObject = (responseFiller.propertyValueOutRangeResponse(propertyRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return propertyObject;
            }
        }
        if (!validator.writeAccessValidator(properties.getAccess())) {
            try {
                propertyObject = responseFiller.propertyNotWriteableResponse(propertyRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return propertyObject;
        }
        try {
            propertyObject = (responseFiller.propertySetResponse(propertyRequest));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return propertyObject;
    }
}
