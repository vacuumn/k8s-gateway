package com.hazelcast.k8s.gateway.rest;

import com.hazelcast.k8s.gateway.api.DeploymentService;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/deployments")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class DeploymentController {

    @Autowired
    DeploymentService deploymentService;

    @GetMapping(produces = {"application/hal+json"})
    ListDeploymentResponse getDeployments(@RequestBody @Valid ListDeploymentRequest req) throws GatewayException {
        return deploymentService.listDeployments(req);
    }

    @PostMapping(consumes = {"application/json"})
    void createDeployment() throws GatewayException {
        deploymentService.createDeployment();
    }

}
