package com.hazelcast.k8s.gateway.dto;

import io.kubernetes.client.models.V1Deployment;

import java.util.List;
import java.util.stream.Collectors;

public class ListDeploymentResponse {

    private final List<Deployment> deployments;

    public static ListDeploymentResponse parseFrom(List<V1Deployment> items) {
        List<Deployment> deployments =  items.stream().map(
                deployment -> new Deployment(
                        deployment.getMetadata().getName(),
                        deployment.getSpec().getTemplate().getSpec().getContainers().stream().map(c -> c.getImage()).collect(Collectors.toList()),
                        deployment.getMetadata().getLabels()
                )).collect(Collectors.toList());
        return new ListDeploymentResponse(deployments);
    }

    public ListDeploymentResponse(List<Deployment> deployments) {
        this.deployments = deployments;
    }

    public List<Deployment> getDeployments() {
        return deployments;
    }
}
