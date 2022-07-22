package io.swagger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-07-22T04:01:22.816Z[GMT]")
@Configuration
public class SwaggerDocumentationConfig {

    @Value("${openapi.project.title}")
    private String title;

    @Value("${openapi.project.description}")
    private String description;

    @Value("${openapi.project.version}")
    private String version;

    @Value("${openapi.project.terms-of-service-url}")
    private String termOfServiceUrl;

    @Value("${openapi.project.contact.name}")
    private String contactName;

    @Value("${openapi.project.contact.email}")
    private String contactEmail;

    @Value("${openapi.project.contact.url}")
    private String contactUrl;

    @Value("${openapi.project.license.name}")
    private String licenseName;

    @Value("${openapi.project.license.url}")
    private String licenseUrl;

    @Value("${openapi.project.external-documentation-description}")
    private String documentationDescription;

    @Value("${openapi.project.external-documentation-url}")
    private String documentationUrl;

    @Bean
    public OpenAPI customImplementation() {
        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8080");
        Server testServer = new Server();
        testServer.setDescription("test");
        testServer.setUrl("https://example.org");
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(info());
        openAPI.setExternalDocs(externalDocumentation());
        openAPI.setServers(Arrays.asList(localServer, testServer));
        return openAPI;
    }

    private Info info() {
        return new Info()
                .title(title)
                .description(description)
                .termsOfService(termOfServiceUrl)
                .version(version)
                .contact(new Contact()
                        .name(contactName)
                        .url(contactUrl)
                        .email(contactEmail))
                .license(new License()
                        .name(licenseName)
                        .url(licenseUrl));
    }

    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description(documentationDescription)
                .url(documentationUrl);
    }

    @Bean
    public GroupedOpenApi apiAuthenticationGroup() {
        String[] paths = {"/authentication/**"};
        String packagesToScan[] = {"io.swagger.api"};
        return GroupedOpenApi.builder()
                .group("authentication")
                .packagesToScan(packagesToScan)
                .pathsToMatch(paths)
                .build();
    }

}
