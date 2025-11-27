package webservices;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ApplicationMain extends ResourceConfig {
    public ApplicationMain() {
        packages("webservices");
    }
}
