package com.hazelcast.k8s.gateway.service;

import com.hazelcast.k8s.gateway.api.DeploymentService;
import com.hazelcast.k8s.gateway.dto.Deployment;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.AppsV1beta1Api;
import io.kubernetes.client.apis.AppsV1beta2Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.AppsV1beta1DeploymentList;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1DeploymentList;
import io.kubernetes.client.models.V1beta2DeploymentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class K8sDeploymentService implements DeploymentService {

    @Autowired
    AppsV1Api appsApi;

    Logger log = LoggerFactory.getLogger("DeploymentService");


    public ListDeploymentResponse listDeployments(ListDeploymentRequest request) throws GatewayException {
        log.info("listDeployment request {} goes to API...", request);
        V1DeploymentList response = null;
        try {
            response = appsApi.listDeploymentForAllNamespaces(request._continue,
                    request.fieldSelector, request.include, request.labelSelector, request.limit, request.pretty,
                    request.resourceVersion, request.timeoutSeconds, request.watch);

        } catch (ApiException apiEx) {
            log.error("Failed to list deployments", apiEx);
            throw new GatewayException(apiEx, "listDeployments");
        }
        log.info("listDeployment response from API: {} deployments", response.getItems().size());
        return ListDeploymentResponse.parseFrom(response.getItems());
    }

    public void createDeployment() throws GatewayException {
        String namespace = null;
        V1Deployment body = null;
        String pretty = null;
        String dryRun = null;
        String fieldManager = null;
        try {
            V1Deployment response = appsApi.createNamespacedDeployment(namespace, body, true, pretty, dryRun);
        } catch (ApiException e) {
            log.error("Failed to list deployments", e);
            throw new GatewayException(e, "createDeployment");
        }

    }
}
