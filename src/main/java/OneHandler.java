import org.eclipse.jetty.server.Server;

public class OneHandler
{
    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(9880);
        server.setHandler(new HelloHandler());
        server.start();
        server.join();
    }
}