package com.hazelcast.k8s.gateway.rest;

import com.hazelcast.k8s.gateway.api.DeploymentService;
import com.hazelcast.k8s.gateway.dto.CreateDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.Deployment;
import com.hazelcast.k8s.gateway.dto.ListDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.ListDeploymentResponse;
import com.hazelcast.k8s.gateway.error.GatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/deployments")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class DeploymentController {

    @Value("${dev.gateway.default.list.limit}")
    private int listLimit;

    @Autowired
    DeploymentService deploymentService;

    @GetMapping(produces = {"application/hal+json"})
    @ResponseBody CollectionModel<Deployment>
    getDeployments(ListDeploymentRequest req) throws GatewayException {
        if (req.limit == null) {
            req.limit = listLimit;
        }

        ListDeploymentResponse listDeploymentResponse = deploymentService.listDeployments(req);

        return new CollectionModel(listDeploymentResponse.getDeployments().stream().map(
                deployment -> new EntityModel<Deployment>(deployment,
                        linkTo(DeploymentController.class).withRel("deployments"))).collect(Collectors.toList()),
                linkTo(DeploymentController.class).withRel("deployments"),
                linkTo(methodOn(DeploymentController.class).getDeployments(req)).withSelfRel());
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseBody
    EntityModel<Deployment>
    createDeployment(@RequestBody @Valid CreateDeploymentRequest request) throws GatewayException {
        return new EntityModel<Deployment>(
                deploymentService.createDeployment(request),
                linkTo(DeploymentController.class).withRel("deployments"));
    }

}
