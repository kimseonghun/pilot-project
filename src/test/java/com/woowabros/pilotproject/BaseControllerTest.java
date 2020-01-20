package com.woowabros.pilotproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {
    protected static final String LOGIN_NAME = "loginName";
    protected static final String LOGIN_PASSWORD = "loginPassword";
    private static final String LOCALHOST = "http://localhost:";
    protected static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modules(new JavaTimeModule())
            .build();

    protected WebTestClient webTestClient;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    protected void setUp(RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl(LOCALHOST + port)
                .filter(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .responseTimeout(Duration.ofMillis(15000))
                .build();
    }

    protected static String getUrl(Class<?> clazz) {
        return linkTo(clazz).toString();
    }

    protected WebTestClient.ResponseSpec login() {
        return webTestClient.post()
                .uri("/member/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("memberName", LOGIN_NAME)
                        .with("password", LOGIN_PASSWORD))
                .exchange();
    }
}

