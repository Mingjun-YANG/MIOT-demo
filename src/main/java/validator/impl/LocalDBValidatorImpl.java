package validator.impl;

import db.impl.DeviceDBLocalJsonImpl;
import org.json.JSONArray;
import typedef.Device;
import typedef.Instance;
import typedef.Services;

import java.util.List;

public class LocalDBValidatorImpl {

    public boolean deviceRequestValidator(String requestDid, String uid) {
        boolean flag = true;
        DeviceDBLocalJsonImpl deviceDB = new DeviceDBLocalJsonImpl();
        List<Device> list = deviceDB.getDevices(uid);
        for (Device aList : list) {
            if (aList.toString().contains(requestDid)) {
                flag = false;
            }
        }
        return flag;
    }

    public String tokenValidator(String token) {
        DeviceDBLocalJsonImpl deviceDB = new DeviceDBLocalJsonImpl();
        return deviceDB.getUid(token);
    }

    public boolean serviceRequestValidator(Instance instance, int requestSiid) {
        return instance.getServices().length() >= requestSiid && requestSiid >= 0;
    }

    public boolean propertyRequestValidator(Services services, int requestPiid) {
        return services.getProperties().length() >= requestPiid && requestPiid >= 0;
    }

    public boolean actionRequestValidator(Services services, int requestAiid) {
        return services.getActions().length() >= requestAiid && requestAiid >= 0;
    }

    public boolean subscribeRequestValidator(Instance instance, String subscribeId) {
        return instance.getSubscriptionId().equals(subscribeId);
    }


    public boolean valueRangeValidator(JSONArray valueRange, Object value, String valueFormat) {
        switch (valueFormat) {
            case "bool":
                return value.equals(true) || value.equals(false);
            case "uint8":
            case "uint16":
            case "uint32":
                int low = valueRange.optInt(0);
                int high = valueRange.optInt(1);
                int pace = valueRange.optInt(2);
                return (int) value <= high && (int) value >= low;

            default:
                return false;
        }
    }

    public boolean valueFormatValidator(String valueFormat, Object value) {
        switch (valueFormat) {
            case "bool":
                return value.toString().equals("true") || value.toString().equals("false");
            case "uint8":
            case "uint16":
            case "uint32":
                return value instanceof Integer;
            case "float":
                return value instanceof Float;
            case "string":
                return value instanceof String;
            default:
                return false;
        }
    }

    public boolean writeAccessValidator(JSONArray access) {
        return access.toString().contains("write");
    }

    public boolean readAccessValidator(JSONArray access) {
        return access.toString().contains("read");
    }


}
