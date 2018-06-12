package device;

import typedef.ActionOperation;
import typedef.PropertyOperation;

public interface Operation {

    void get(PropertyOperation property);

    void set(PropertyOperation property);

    void invoke(ActionOperation action);
}
