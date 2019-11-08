package com.hazelcast.k8s.gateway.config;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
public class K8sConfig {

    @Value("${dev.gateway.k8s-client.config}")
    private String kubeConfigPath;

    @Bean
    AppsV1Api initK8sApiClient() throws IOException {
        try {
            FileReader reader = new FileReader(kubeConfigPath);
            ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(reader)).build();
            io.kubernetes.client.Configuration.setDefaultApiClient(client);
            //Potentially we can populate more API facades into context, but for the sake of simplicity lets skip it
            return new AppsV1Api();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize K8s client from config", e);
        }
    }

}
