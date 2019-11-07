package com.hazelcast.k8s.gateway.service;

import com.hazelcast.k8s.gateway.api.DeploymentService;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsApi;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1DeploymentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class K8sDeploymentService implements DeploymentService {
    Logger log = LoggerFactory.getLogger("DeploymentService");

    private final AppsV1Api appsApi;


    public K8sDeploymentService(@Autowired AppsV1Api appsApi) {
        this.appsApi = appsApi;
    }


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
        log.info("listDeployment response from API: {} deployment(s)", response.getItems().size());
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
            log.error("Failed to create deployment", e);
            throw new GatewayException(e, "createDeployment");
        }

    }
}
