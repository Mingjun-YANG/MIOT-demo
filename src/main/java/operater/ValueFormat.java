package operater;

import java.util.HashSet;
import java.util.Set;

public class ValueFormat {
    private static final Set<String> boolTrueSet = new HashSet<>();

    static {
        boolTrueSet.add("true");
        boolTrueSet.add("on");
        boolTrueSet.add("yes");
        boolTrueSet.add("open");
    }

    public static int toInteger(Object value){
        int result = 0;
            if (value instanceof String) {
                result = Integer.valueOf((String) value);
            } else {
                result = (int) value;
            }
        return result;
    }

    public static boolean toBoolean(Object value) {
        boolean result = true;

            if (value instanceof String) {
                result = boolTrueSet.contains(((String) value).toLowerCase());
            } else {
                result = (boolean) value;
            }

        return result;
    }

    public static Float toFloat(Object value){
        Float result = 0.0F;
            if (value instanceof String) {
                result = Float.valueOf((String) value);
            } else if (value instanceof Number) {
                result = ((Number) value).floatValue();
            }

        return result;
    }

    public static Double toDouble(Object value) {
        Double result = 0.0D;
            if (value instanceof String) {
                result = Double.valueOf((String) value);
            } else {
                result = (double) value;
            }
        return result;
    }

    public static Object converter (String st) {
        Object result = 0;
        if (st.equals("uint8")  || st.equals("uint16")  || st.equals("uint32") ) {
            result = 1;
            return result;
        } else if (st.equals("float")) {
            result = 1.0F;
            return result;
        } else if (st.equals("string")) {
            result = "string";
            return result;
        } else if (st.equals("bool")) {
            result = true;
            return result;
        }
        return result;
    }
}

