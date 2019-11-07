package com.hazelcast.k8s.gateway.service;

import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1DeploymentList;
import org.junit.Before;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@RunWith(JUnit4ClassRunner.class)
class K8sDeploymentServiceTest {

    K8sDeploymentService service;
    AppsV1Api appsV1Api;

    @BeforeEach
    public void init() {
        //  MockitoAnnotations.initMocks(this);
        appsV1Api = mock(AppsV1Api.class, withSettings().verboseLogging());
        service = new K8sDeploymentService(appsV1Api);
    }

    @Test
    void testListDeployments() throws ApiException {
        V1DeploymentList mockedResponse = new V1DeploymentList().items(Collections.singletonList(new V1Deployment()));
        when(appsV1Api.listDeploymentForAllNamespaces(any(), any(), anyBoolean(), any(), any(), any(), any(), any(), anyBoolean()))
                .thenReturn(mockedResponse);
        ListDeploymentRequest request = new ListDeploymentRequest();
        ListDeploymentResponse listDeploymentResponse = service.listDeployments(request);
        assertNotNull(listDeploymentResponse);

        verify(appsV1Api).listDeploymentForAllNamespaces(any(), any(), any(), any(), any(), any(), any(), any(), anyBoolean());
        verifyNoMoreInteractions(appsV1Api);
    }

    @Test
    void createDeployment() {
    }
}