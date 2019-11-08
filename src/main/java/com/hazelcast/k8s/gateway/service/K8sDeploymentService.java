package com.hazelcast.k8s.gateway.service;

import com.hazelcast.k8s.gateway.api.DeploymentService;
import com.hazelcast.k8s.gateway.dto.CreateDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.Deployment;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;
import com.hazelcast.k8s.gateway.repository.DeploymentEntity;
import com.hazelcast.k8s.gateway.repository.DeploymentRepository;
import com.hazelcast.k8s.gateway.service.mappers.K8sDeploymentMapper;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1DeploymentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;

@Component
public class K8sDeploymentService implements DeploymentService {
    private Logger log = LoggerFactory.getLogger("DeploymentService");
    private static final String DEFAULT_LABEL_KEY = "origin";

    private final DeploymentRepository repository;
    private final AppsV1Api appsApi;

    public K8sDeploymentService(DeploymentRepository repository, AppsV1Api appsApi) {
        this.repository = repository;
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
            log.error("API operation failed to list deployments", apiEx);
            throw new GatewayException(apiEx, "listDeployments");
        } catch (Exception ex) {
            log.error("Failed to list deployments", ex);
            throw new GatewayException(ex, "listDeployments");
        }
        log.info("listDeployment response from API: {} deployment(s)", response.getItems().size());
        return ListDeploymentResponse.parseFrom(response.getItems());
    }

    public Deployment createDeployment(CreateDeploymentRequest request) throws GatewayException {
        putLabels(request.deployment);
        V1Deployment response = null;
        try {
            response = appsApi.createNamespacedDeployment(request.namespace, K8sDeploymentMapper.fromDeployment(request.deployment),
                    request.includeUninitialized, request.pretty, request.dryRun);
        } catch (ApiException e) {
            log.error("API operation failed to create deployment", e);
            throw new GatewayException(e, "createDeployment");
        } catch (Exception ex) {
            log.error("Failed to create deployment", ex);
            throw new GatewayException(ex, "createDeployment");
        }
        log.info("createDeployment response from API: deployment {} is in {} status", response.getMetadata().getName(), response.getStatus());

        saveDeploymentToRepo(response, request);
        return ListDeploymentResponse.parseFrom(Collections.singletonList(response)).getDeployments().get(0);
    }

    private DeploymentEntity saveDeploymentToRepo(V1Deployment response, CreateDeploymentRequest request) {
        return repository.save(new DeploymentEntity(response.getMetadata().getName(), request.deployment.getImages()));
    }

    private void putLabels(Deployment deployment) {
        if (deployment.getLabels() == null) {
            deployment.setLabels(new HashMap<>());
        }
        deployment.getLabels().put(DEFAULT_LABEL_KEY, this.getClass().getName());
    }
}
