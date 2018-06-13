package device.impl;

import device.Operation;
import typedef.ActionOperation;
import typedef.PropertyOperation;

public class OperationMockImpl implements Operation {

    /**
     * 读属性
     *  - 如果成功， status = 0， 且返回属性值
     *  - 如果失败， status为负值，且携带description（见文档《第三方设备云接入小米IOT平台》）
     * @param property
     */
    @Override
    public void get(PropertyOperation property) {

        // 如果成功， status = 0， 且返回属性值
        if (property.did.equals("AAAA")) {
            property.status = 0;
            property.value = 13;
        } else
            // 如果失败， status为负值，且携带description（见文档《第三方设备云接入小米IOT平台》）
            property.status = -1;
        property.description = "did invalid";
    }

    @Override
    public void set(PropertyOperation property) {
        // TODO: 写属性,
        if (!property.did.equals("AAAA")) {
            property.status = -1;
            property.description = "did invalid";
        } else if ((Integer) property.value > 100) {
            property.status = -10;
            property.description = "value out of range";
        } else {
            // 如果成功， status=0
            property.status = 0;
        }

        // 如果失败， status为负值，且携带description（见文档《第三方设备云接入小米IOT平台》）

    }

    @Override
    public void invoke(ActionOperation action) {
        // TODO: 执行方法
        if (!action.did.equals("AAAA")) {
            // 如果失败， status为负值，且携带description（见文档《第三方设备云接入小米IOT平台》）
            action.status = -1;
            action.description = "did invalid";
        } else {
            action.status = 0;
            // 如果此方法有返回值，需要正确填写返回值，如：
            action.out.add(19);
            action.out.add("beijing");
        }
    }
}
