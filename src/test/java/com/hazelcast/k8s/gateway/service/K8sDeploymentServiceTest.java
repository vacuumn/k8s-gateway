package com.hazelcast.k8s.gateway.service;

import com.hazelcast.k8s.gateway.dto.CreateDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.Deployment;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;
import com.hazelcast.k8s.gateway.repository.DeploymentRepository;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class K8sDeploymentServiceTest {

    K8sDeploymentService service;
    AppsV1Api appsV1Api;

    V1Deployment deployment = new V1Deployment().
            metadata(new V1ObjectMeta().
                    name("testdeploy"))
            .spec(new V1DeploymentSpec()
                    .template(new V1PodTemplateSpec()
                            .spec(new V1PodSpec()
                                    .containers(Collections.singletonList(new V1Container()
                                            .image("testImage"))))));
    V1DeploymentList deploymentList = new V1DeploymentList().items(Collections.singletonList(deployment));

    @BeforeEach
    public void init() {
        appsV1Api = mock(AppsV1Api.class, withSettings().verboseLogging());
        DeploymentRepository repository =  mock(DeploymentRepository.class);
        service = new K8sDeploymentService(repository, appsV1Api);
    }

    @Test
    void testListDeployments() throws ApiException, GatewayException {
        when(appsV1Api.listDeploymentForAllNamespaces(isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull()))
                .thenReturn(deploymentList);
        ListDeploymentRequest request = new ListDeploymentRequest();

        ListDeploymentResponse listDeploymentResponse = service.listDeployments(request);

        assertNotNull(listDeploymentResponse);
        assertEquals(1, listDeploymentResponse.getDeployments().size());
        assertEquals(deployment.getMetadata().getName(), listDeploymentResponse.getDeployments().get(0).getName());

        verify(appsV1Api).listDeploymentForAllNamespaces(isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull());
        verifyNoMoreInteractions(appsV1Api);
    }

    @Test
    void testListDeploymentsThrowException() throws Exception {
        when(appsV1Api.listDeploymentForAllNamespaces(isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull()))
                .thenThrow(new ApiException());


        Assertions.assertThrows(GatewayException.class, () -> {
            service.listDeployments(new ListDeploymentRequest());
        });
    }

    @Test
    void  testCreateDeployment() throws Exception {
        when(appsV1Api.createNamespacedDeployment(isNull(), any(), isNull(), isNull(), isNull()))
                .thenReturn(deployment);
        CreateDeploymentRequest request = new CreateDeploymentRequest();
        request.deployment = new Deployment(deployment.getMetadata().getName(), Collections.singletonList("testImage"), null);

        Deployment result = service.createDeployment(request);

        verify(appsV1Api).createNamespacedDeployment(isNull(), any(), isNull(), isNull(), isNull());

        assertNotNull(result);
        assertEquals(deployment.getMetadata().getName(), result.getName());
    }

    @Test
    void testCreateDeploymentThrowException() throws Exception {
        when(appsV1Api.createNamespacedDeployment(isNull(), any(), isNull(), isNull(), isNull()))
                .thenThrow(new ApiException());
        CreateDeploymentRequest request = new CreateDeploymentRequest();
        request.deployment = new Deployment(deployment.getMetadata().getName(), Collections.singletonList("testImage"), null);

        Assertions.assertThrows(GatewayException.class, () -> {
            service.createDeployment(request);
        });
    }
}