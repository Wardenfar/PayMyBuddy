package com.wardenfar.paymybuddy.cypress;

import com.wardenfar.paymybuddy.service.UserService;
import io.github.wimdeblauwe.testcontainers.cypress.CypressContainer;
import io.github.wimdeblauwe.testcontainers.cypress.CypressTestResults;
import io.github.wimdeblauwe.testcontainers.cypress.MochawesomeGatherTestResultsStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.Testcontainers;

import java.nio.file.FileSystems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class CypressIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Test
    void runCypressTests() throws Exception {
        Testcontainers.exposeHostPorts(port);

        MochawesomeGatherTestResultsStrategy gradleTestResultStrategy = new MochawesomeGatherTestResultsStrategy(
                FileSystems.getDefault().getPath("build", "resources", "test", "e2e", "cypress", "reports", "mochawesome"));

        try (CypressContainer container = new CypressContainer()
                .withLocalServerPort(port)
                .withGatherTestResultsStrategy(gradleTestResultStrategy)) {

            userService.createUser("admin@gmail.com", "Admin", "ADMIN", "testtest");
            System.out.println("start");
            container.start();
            CypressTestResults testResults = container.getTestResults();

            System.out.println(testResults.getNumberOfPassingTests() + " " + testResults.getNumberOfFailingTests());
        }
    }
}
