package miot;

import typedef.ActionRequest;
import typedef.PropertyRequest;
import org.json.JSONObject;
import typedef.SubscribeRequest;

import java.util.List;

public interface Request {

    List<PropertyRequest> getPropertyRequest (JSONObject context);

    List<PropertyRequest> setPropertyRequest (JSONObject context);

    List<ActionRequest> excecuteActionRequest (JSONObject context);

    List<SubscribeRequest> subscribeRequest (JSONObject context);


}


