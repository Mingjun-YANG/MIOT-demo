package subscribe.impl;

import subscribe.Subscribe;
import typedef.SubscribeOperation;

public class SubscribeMockImpl implements Subscribe {
    @Override
    public void set(SubscribeOperation subscribe){
        if (subscribe.did.equals("AAAA") && (subscribe.subscriptionId.equals("123456")  || subscribe.subscriptionId.equals("abcdefg"))) {
            subscribe.status = 0;
        }else
            subscribe.status = -1;
            subscribe.description = "invalid devide id";
    }

    @Override
    public void unset(SubscribeOperation subscribe){
        if (subscribe.did.equals("AAAA") && subscribe.subscriptionId.equals("123456")) {
            subscribe.status = 0;
        }else
            subscribe.status = -16;
        subscribe.description = "invalid subscriptionId";
    }
}
