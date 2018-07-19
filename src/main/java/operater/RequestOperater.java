package operater;

import miot.MiotRequest;
import org.json.JSONArray;
import typedef.ActionRequest;
import typedef.PropertyRequest;
import typedef.SubscribeRequest;

import java.util.List;

public interface RequestOperater {

    JSONArray actionRequestOperater(List<ActionRequest> list, MiotRequest req);

    JSONArray subscribeRequestOperater(List<SubscribeRequest> list, MiotRequest req);

    JSONArray deviceStatusRequestOperater(JSONArray devices, MiotRequest req);

    JSONArray propertyRequestOperater(List<PropertyRequest> list, MiotRequest req);


}
