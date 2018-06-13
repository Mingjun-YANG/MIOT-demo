package operater;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HeaderValidator {

    public boolean headerValidator(HttpServletResponse response, String requestId, String intent) throws IOException {
        PrintWriter out = response.getWriter();
        if (requestId == null) {
            response.setStatus(400);
            out = response.getWriter();
            out.println("requestId is null");
            return false;
        } else {
            if (intent == null) {
                response.setStatus(400);
                out = response.getWriter();
                out.println("intent is null");
                return false;
            }
            return true;
        }
    }
}

