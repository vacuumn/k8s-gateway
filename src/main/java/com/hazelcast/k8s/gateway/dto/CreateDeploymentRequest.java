package com.hazelcast.k8s.gateway.dto;


import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class CreateDeploymentRequest {
    //@NotEmpty(message = "Namespace may not be empty")
    public String namespace;
    public @Valid Deployment deployment;
    public String pretty;
    public String dryRun;
    public boolean includeUninitialized;

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }
}
