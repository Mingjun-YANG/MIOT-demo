package operater;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;


public class ResponseOperater {
    public static void outPrinter(JSONObject o, String intent, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        if (o == null) {
            response.setStatus(400);
            out.println(intent + " is null");
        } else {
            response.setStatus(200);
            out.println(o);
        }
    }

}
