package com.hazelcast.k8s.gateway.service.mappers;

import com.hazelcast.k8s.gateway.dto.CreateDeploymentRequest;
import com.hazelcast.k8s.gateway.dto.Deployment;
import io.kubernetes.client.models.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class K8sDeploymentMapper {

    private static final String API_VERSION = "apps/v1";

    public static V1Deployment fromDeployment(Deployment deployment) {
        V1PodSpec v1PodSpec = new V1PodSpec()
                .containers(deployment.getImages().stream()
                                .map(image -> new V1Container()
                                        .name(deployment.getName())
                                        .image(image))
                                .collect(Collectors.toList())
                );

        Map<String, String> labels = deployment.getLabels();


        V1DeploymentSpec v1DeploymentSpec = new V1DeploymentSpec()
                .selector(new V1LabelSelector().
                        matchLabels(labels)
                )
                .template(
                        new V1PodTemplateSpec()
                                .metadata(new V1ObjectMeta().labels(labels))
                                .spec(v1PodSpec)
                );


        V1Deployment v1Deployment = new V1Deployment();
        v1Deployment
                .apiVersion(API_VERSION)
                .kind("Deployment")
                .metadata(new V1ObjectMeta()
                        .name(deployment.getName())
                        .labels(labels)
                )
                .spec(v1DeploymentSpec);


        return v1Deployment;
    }
}
