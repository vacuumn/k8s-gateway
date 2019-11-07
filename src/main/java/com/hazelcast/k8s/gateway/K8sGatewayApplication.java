package com.hazelcast.k8s.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class K8sGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(K8sGatewayApplication.class, args);
    }

}
