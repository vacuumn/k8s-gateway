package com.hazelcast.k8s.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import io.kubernetes.client.apis.AppsV1Api;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class K8sGatewayApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AppsV1Api appsApi;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listDeploymentsWithSomeFiltersShouldReturnOK() throws Exception {
        ListDeploymentRequest request = new ListDeploymentRequest();
        request.limit = 1;
        request.labelSelector = "k8s";

        mockMvc.perform(get("/v1/deployments")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void listDeploymentsWhenNoParamsProvidedShouldReturnOK() throws Exception {
        mockMvc.perform(get("/v1/deployments")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test()
    void listDeploymentsShouldFailDueToConnectTimeout() throws Exception {
        //Scramble the address to k8s APIserver
        String basePath = appsApi.getApiClient().getBasePath();
        appsApi.getApiClient().setBasePath("https://1.1.1.1:8443");
        try{
            mockMvc.perform(get("/v1/deployments")
                    .contentType("application/json"))
                    .andExpect(status().is5xxServerError());
        } finally {
            appsApi.getApiClient().setBasePath(basePath);
        }
    }



}
