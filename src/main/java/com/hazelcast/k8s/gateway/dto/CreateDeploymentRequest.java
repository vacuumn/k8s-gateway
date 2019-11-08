package com.hazelcast.k8s.gateway.dto;


import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class CreateDeploymentRequest {
    @NotEmpty(message = "Namespace may not be empty")
    public String namespace;
    public @Valid Deployment deployment;
    @Nullable
    public String pretty;
    @Nullable
    public String dryRun;
    @Nullable
    public Boolean includeUninitialized;

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }
}
