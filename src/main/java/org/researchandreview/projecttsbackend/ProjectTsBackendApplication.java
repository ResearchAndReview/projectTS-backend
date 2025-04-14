package org.researchandreview.projecttsbackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {
        @Server(url = "https://js.thxx.xyz", description = "HTTPS (Secure)"),
        @Server(url = "http://js.thxx.xyz", description = "HTTP (Insecure)"),
        @Server(url = "http://localhost:8080", description = "LOCAL")
})
@SpringBootApplication
public class ProjectTsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectTsBackendApplication.class, args);
    }

}
