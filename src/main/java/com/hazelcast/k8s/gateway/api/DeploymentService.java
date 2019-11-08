package com.hazelcast.k8s.gateway.api;

import com.hazelcast.k8s.gateway.dto.CreateDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.Deployment;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;

import java.util.List;

/**
 *  General interface for all deployments interaction...
 */
public interface DeploymentService {

    /**
     * List deployments for all namespaces.
     *
     * @param request DTO with request params
     * @return holder with found deployments
     * @throws GatewayException in case of errors during API request process
     */
    ListDeploymentResponse listDeployments(ListDeploymentRequest request) throws GatewayException;

    /**
     * Create new deployment.
     *
     * @param request DTO with deployment body and supported options
     * @return found deployment DTO
     * @throws GatewayException  in case of errors during API request process
     */
    Deployment createDeployment(CreateDeploymentRequest request) throws GatewayException;

}
