package com.hazelcast.k8s.gateway.api;

import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;

import java.util.List;

/**
 * TODO:
 */
public interface DeploymentService {

    public ListDeploymentResponse listDeployments(ListDeploymentRequest request) throws GatewayException;

    public void createDeployment() throws GatewayException;

}
