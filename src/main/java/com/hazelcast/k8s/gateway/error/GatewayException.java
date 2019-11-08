package com.hazelcast.k8s.gateway.error;


import io.kubernetes.client.ApiException;

/**
 *  Wrap k8s client exception with our own to handle gracefuly and strip out sensitive data if necessary.
 */
public class GatewayException extends Exception {

    public GatewayException(Exception parent, String operation) {
        super(String.format("Could not execute gateway operation %s", operation), parent);
    }
}
