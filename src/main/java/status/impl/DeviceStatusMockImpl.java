package status.impl;

import status.Status;
import typedef.StatusOperation;

public class DeviceStatusMockImpl implements Status {

    @Override
    public void get(StatusOperation deviceStatus) {
        // TODO: 读属性,

        // 如果成功， status = 0， 且返回属性值
        if (deviceStatus.did.equals("AAAA")) {
            deviceStatus.status = 0;
            deviceStatus.name = "whitee";
            deviceStatus.online = "true";
        } else {
            // 如果失败， status为负值，且携带description（见文档《第三方设备云接入小米IOT平台》）
            deviceStatus.status = -1;
            deviceStatus.description = "invalid device Id";
        }
    }
}
