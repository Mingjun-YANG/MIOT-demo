package operater;

import org.json.JSONArray;
import typedef.ActionRequest;
import typedef.PropertyRequest;
import typedef.StatusRequest;
import typedef.SubscribeRequest;

import java.util.List;

public interface RequestOperater {

    JSONArray actionRequestOperater(String uid, List<ActionRequest> list);

    JSONArray subscribeRequestOperater(String uid, List<SubscribeRequest> list);

    JSONArray deviceStatusRequestOperater(String uid, List<StatusRequest> list);

    JSONArray setProperties(String uid, List<PropertyRequest> list) throws Exception;

    JSONArray getProperties(String uid, List<PropertyRequest> list) throws Exception;



}
