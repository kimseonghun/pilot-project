package com.woowabros.pilotproject;

import com.woowabros.pilotproject.domain.member.controller.MemberApiController;
import com.woowabros.pilotproject.domain.member.dto.MemberLoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {
    private static final String LOCALHOST = "http://localhost:";
    protected static final String LOGIN_NAME = "loginName";
    protected static final String LOGIN_PASSWORD = "loginPassword";

    protected WebTestClient webTestClient;
    protected String cookie;

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

        this.cookie = getCookie();
    }

    protected static String getUrl(Class<?> clazz) {
        return linkTo(clazz).toString();
    }

    protected WebTestClient.ResponseSpec login() {
        return webTestClient.post()
                .uri(getUrl(MemberApiController.class) + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(MemberLoginDto.builder()
                        .memberName(LOGIN_NAME)
                        .password(LOGIN_PASSWORD)
                        .build())
                .exchange();
    }

    private String getCookie() {
        return login()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }
}

