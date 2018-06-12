import org.eclipse.jetty.server.Server;

public class MainHandler {
    public static void main(String[] args) throws Exception {
        Server server = new Server(9880);
        server.setHandler(new ServiceHandler());
        server.start();
        server.join();
    }
}