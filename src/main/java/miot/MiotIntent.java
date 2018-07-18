package miot;

public enum MiotIntent {

    UNDEFINED("undefined"),
    GET_DEVICES("get-devices"),
    GET_PROPERTIES("get-properties"),
    SET_PROPERTIES("set-properties"),
    INVOKE_ACTION("invoke-action"),
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
    GET_DEVICE_STATUS("get-device-status");


    private String value;

    MiotIntent(String v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return value;
    }

    public static MiotIntent from(String string) {
        for (MiotIntent t : MiotIntent.values()) {
            if (t.value.equals(string)) {
                return t;
            }
        }

        return UNDEFINED;
    }
}
