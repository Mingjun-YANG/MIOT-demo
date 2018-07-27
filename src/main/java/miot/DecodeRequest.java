package miot;

import miot.request.*;
import typedef.ActionRequest;
import typedef.PropertyRequest;
import typedef.StatusRequest;
import typedef.SubscribeRequest;

import java.util.List;

public interface DecodeRequest {

    List<PropertyRequest> decodeGetPropertyRequest(MiotGetPropertiesRequest req);

    List<PropertyRequest> decodeSetPropertyRequest(MiotSetPropertiesRequest req);

    List<ActionRequest> decodeActionRequest(MiotActionRequest req);

    List<SubscribeRequest> decodeSubscribeRequest(MiotSubscribeRequest req);

    List<StatusRequest> decodeStatusRequest(MiotGetStatusRequest req);


}


