/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manageo.api.gateway;

import com.manageo.api.gataway.ApiGataway;
import com.manageo.api.gateway.entity.AuthRequest;
import com.manageo.api.gateway.entity.AuthResponse;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author wassim
 */
@SpringBootTest(classes = ApiGataway.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GatewayE2ETest {

    @LocalServerPort
    private int port;

    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCompleteWorkflow() {

        String authUrl = "http://localhost:" + port + "/auth/login";
        AuthRequest loginRequest = new AuthRequest("user", "pass");
        ResponseEntity<AuthResponse> authResponse = restTemplate.postForEntity(authUrl, loginRequest, AuthResponse.class);
        String jwt = authResponse.getBody().getJwtToken();

        String reservationUrl = "http://localhost:" + port + "/reservations";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(reservationUrl, HttpMethod.GET, entity, String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

}
