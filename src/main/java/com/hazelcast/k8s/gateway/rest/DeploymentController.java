package com.hazelcast.k8s.gateway.rest;

import com.hazelcast.k8s.gateway.api.DeploymentService;
import com.hazelcast.k8s.gateway.dto.CreateDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.Deployment;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/v1/deployments")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class DeploymentController {

    @Value("${dev.gateway.default.list.limit}")
    private int listLimit;

    @Autowired
    DeploymentService deploymentService;

    @GetMapping(produces = {"application/hal+json"})
    @ResponseBody ListDeploymentResponse
    getDeployments(ListDeploymentRequest req) throws GatewayException {
        if (req.limit == null) {
            req.limit = listLimit;
        }
        return deploymentService.listDeployments(req);
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseBody
    EntityModel<Deployment>
    createDeployment(@RequestBody @Valid CreateDeploymentRequest request) throws GatewayException {
        return new EntityModel<Deployment>(deploymentService.createDeployment(request),
                linkTo(DeploymentController.class).withRel("deployments"));
    }

}
