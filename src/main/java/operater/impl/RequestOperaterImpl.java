package operater.impl;

import db.impl.DeviceDBLocalJsonImpl;
import miot.EncodeResponse;
import miot.impl.EncodeResponseImpl;
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
    private EncodeResponse encodeResponse = new EncodeResponseImpl();


    @Override
    public JSONArray actionRequestOperater(String uid, List<ActionRequest> list){
        JSONArray actionsArray = new JSONArray();

        list.forEach(ActionRequest -> {
            if (validator.deviceRequestValidator(ActionRequest.getDid(), uid)) {
                try {
                    actionsArray.put(encodeResponse.deviceNotFoundResponse(ActionRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            Instance instance = null;
            try {
                instance = deviceDB.getInstance(ActionRequest.getDid());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!validator.serviceRequestValidator(instance, ActionRequest.getSiid())) {
                try {
                    actionsArray.put(encodeResponse.serviceNotFoundResponse(ActionRequest));
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
                            actionsArray.put(encodeResponse.actionNotFoundResponse(ActionRequest));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return;
                    }

                    List<Actions> actions = deviceDB.getActions(Services);
                    actions.forEach(Actions -> {

                        if (Actions.getIid() == (ActionRequest.getAiid())) {
                            try {
                                actionsArray.put(encodeResponse.actionExcecutedResponse(ActionRequest, Actions));
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
    public JSONArray subscribeRequestOperater(String uid, List<SubscribeRequest> list) {

        JSONArray subscribeArray = new JSONArray();

        list.forEach(SubscribeRequest -> {
            if (validator.deviceRequestValidator(SubscribeRequest.getDid(), uid)) {
                try {
                    subscribeArray.put(encodeResponse.deviceNotFoundResponse(SubscribeRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }

            Instance instance = null;
            try {
                instance = deviceDB.getInstance(SubscribeRequest.getDid());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (validator.subscribeRequestValidator(instance, SubscribeRequest.getSubscriptionId())) {
                try {
                    subscribeArray.put(encodeResponse.subscribeIdInvalidResponse(SubscribeRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }
            try {
                subscribeArray.put(encodeResponse.subscribeExcecutedResponse(SubscribeRequest));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        return subscribeArray;
    }

    public JSONArray deviceStatusRequestOperater(String uid, List<StatusRequest> list) {
        JSONArray deviceStatusArray = new JSONArray();

        list.forEach(StatusRequest -> {
            String did = StatusRequest.getDevice();
            if (validator.deviceRequestValidator(did, uid)) {
                try {
                    deviceStatusArray.put(encodeResponse.deviceNotFoundResponse(did));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }
            Instance instance = null;
            try {
                instance = deviceDB.getInstance(did);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                deviceStatusArray.put(encodeResponse.getStatusResponse(did, instance.getStatus(), instance.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return deviceStatusArray;
    }

    public JSONArray getProperties(String uid, List<PropertyRequest> list) throws Exception{
        JSONArray propertiesArray = new JSONArray();

        for (typedef.PropertyRequest PropertyRequest : list) {
            if (validator.deviceRequestValidator(PropertyRequest.getDid(), uid)) {
                try {
                    propertiesArray.put(encodeResponse.deviceNotFoundResponse(PropertyRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                continue;
            }

            Instance instance = deviceDB.getInstance(PropertyRequest.getDid());
            if (!validator.serviceRequestValidator(instance, PropertyRequest.getSiid())) {
                try {
                    propertiesArray.put(encodeResponse.serviceNotFoundResponse(PropertyRequest));
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
                            propertiesArray.put(encodeResponse.propertyNotFoundResponse(PropertyRequest));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    List<Property> properties = deviceDB.getProperties(Services);
                    properties.forEach(Properties -> {
                        if (Properties.getIid() == (PropertyRequest.getPiid())) {
                            propertiesArray.put(getPropertyRequestOperater(Properties, PropertyRequest));
                        }
                    });
                }
            }
        }
        return propertiesArray;
    }

    public JSONArray setProperties(String uid, List<PropertyRequest> list) throws Exception{
        JSONArray propertiesArray = new JSONArray();

        for (typedef.PropertyRequest PropertyRequest : list) {
            if (validator.deviceRequestValidator(PropertyRequest.getDid(), uid)) {
                propertiesArray.put(encodeResponse.deviceNotFoundResponse(PropertyRequest));

                continue;
            }

            Instance instance = deviceDB.getInstance(PropertyRequest.getDid());
            if (!validator.serviceRequestValidator(instance, PropertyRequest.getSiid())) {
                try {
                    propertiesArray.put(encodeResponse.serviceNotFoundResponse(PropertyRequest));
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
                            propertiesArray.put(encodeResponse.propertyNotFoundResponse(PropertyRequest));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    List<Property> properties = deviceDB.getProperties(Services);
                    properties.forEach(Properties -> {
                        if (Properties.getIid() == (PropertyRequest.getPiid())) {
                            propertiesArray.put(setPropertyRequestOperater(Properties, PropertyRequest));
                        }
                    });
                }
            }
        }
        return propertiesArray;
    }

    private JSONObject getPropertyRequestOperater(Property properties, PropertyRequest propertyRequest) {
        JSONObject propertyObject = new JSONObject();
        if (!validator.readAccessValidator(properties.getAccess())) {
            try {
                propertyObject = encodeResponse.propertyNotReadableResponse(propertyRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return propertyObject;
        }
        try {
            propertyObject = (encodeResponse.propertyGetResponse(propertyRequest, properties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertyObject;
    }

    private JSONObject setPropertyRequestOperater(Property properties, PropertyRequest propertyRequest) {
        JSONObject propertyObject = new JSONObject();
        if (!validator.valueFormatValidator(properties.getFormat(), properties.getValue())) {
            try {
                propertyObject = encodeResponse.propertyInvalidFormatResponse(propertyRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return propertyObject;
        }
        if (propertyRequest.getValue() instanceof Float || propertyRequest.getValue() instanceof Integer) {
            if (!validator.valueRangeValidator(properties.getValue_range(), propertyRequest.getValue(), properties.getFormat())) {
                try {
                    propertyObject = (encodeResponse.propertyValueOutRangeResponse(propertyRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return propertyObject;
            }
        }
        if (!validator.writeAccessValidator(properties.getAccess())) {
            try {
                propertyObject = encodeResponse.propertyNotWriteableResponse(propertyRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return propertyObject;
        }
        try {
            propertyObject = (encodeResponse.propertySetResponse(propertyRequest));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return propertyObject;
    }
}
