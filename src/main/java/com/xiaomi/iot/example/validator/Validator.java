package com.xiaomi.iot.example.validator;

import org.json.JSONArray;
import com.xiaomi.iot.example.typedef.Instance;
import com.xiaomi.iot.example.typedef.Services;

public interface Validator {

    boolean deviceRequestValidator(String requestDid, String uid);

    boolean serviceRequestValidator(Instance instance, int requestSiid);

    boolean subscribeRequestValidator(Instance instance, String subscribeId);

    boolean propertyRequestValidator(Services services, int requestPiid);

    boolean actionRequestValidator(Services services, int requestAiid);

    boolean valueRangeValidator(JSONArray valueRange, Object value, String valueFormat);

    boolean valueFormatValidator(String valueFormat, Object value);

    boolean isWritable(JSONArray access);

    boolean isReadable(JSONArray access);
}
