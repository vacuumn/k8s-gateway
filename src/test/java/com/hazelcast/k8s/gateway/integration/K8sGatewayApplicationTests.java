package com.hazelcast.k8s.gateway.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.k8s.gateway.dto.CreateDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.Deployment;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import io.kubernetes.client.apis.AppsV1Api;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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


    @Test
    void createDeploymentsAndListItShouldReturnOK() throws Exception {
        CreateDeploymentRequest request = new CreateDeploymentRequest();
        request.namespace = "default";

        try{
            request.deployment = new Deployment("testdeploy", Collections.singletonList("nginx:1.7.9"), null);
            MvcResult createResult = mockMvc.perform(post("/v1/deployments")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andReturn();
            System.out.println(createResult.getResponse().getContentAsString());

            MvcResult listResult = mockMvc.perform(get("/v1/deployments")
                    .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andReturn();

            System.out.println(listResult.getResponse().getContentAsString());
        } finally {
            appsApi.deleteNamespacedDeployment(request.deployment.getName(), request.namespace, null, null, null,null , null, null);
        }
   }




}
