package introsde.finalproject.quotes;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class ServiceStandalone {
    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException
    {
    	String PROTOCOL = "http://";
        String PORT = "7004";
        String HOSTNAME = InetAddress.getLocalHost().getHostAddress();
        if (HOSTNAME.equals("127.0.0.1")) {
            HOSTNAME = "localhost";
        }

        URI baseUrl = new URI(PROTOCOL+HOSTNAME+":"+PORT+"/");

        ResourceConfig rc = new ResourceConfig(Service.class);
        JdkHttpServerFactory.createHttpServer(baseUrl, rc);
        System.out.println("Quotes server starts on " + baseUrl + "\n[kill the process to exit]");
    }
}
