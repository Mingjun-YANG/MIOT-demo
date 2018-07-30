package com.xiaomi.iot.example.miot;

import com.xiaomi.iot.example.miot.request.*;
import com.xiaomi.iot.example.typedef.ActionRequest;
import com.xiaomi.iot.example.typedef.PropertyRequest;
import com.xiaomi.iot.example.typedef.StatusRequest;
import com.xiaomi.iot.example.typedef.SubscribeRequest;

import java.util.List;

public interface DecodeRequest {

    List<PropertyRequest> decodeGetPropertyRequest(MiotGetPropertiesRequest req);

    List<PropertyRequest> decodeSetPropertyRequest(MiotSetPropertiesRequest req);

    List<ActionRequest> decodeActionRequest(MiotActionRequest req);

    List<SubscribeRequest> decodeSubscribeRequest(MiotSubscribeRequest req);

    List<StatusRequest> decodeStatusRequest(MiotGetStatusRequest req);


}


