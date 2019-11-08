package com.hazelcast.k8s.gateway.api;

import com.hazelcast.k8s.gateway.dto.CreateDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.Deployment;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;

import java.util.List;

/**
 *
 */
public interface DeploymentService {

    ListDeploymentResponse listDeployments(ListDeploymentRequest request) throws GatewayException;

    Deployment createDeployment(CreateDeploymentRequest request) throws GatewayException;

}
