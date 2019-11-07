package com.hazelcast.k8s.gateway.error;

import io.kubernetes.client.ApiException;

/**
 *  Wrap k8s client exception with our own to handle gracefuly and strip out sensitive data if necessary.
 */
public class GatewayException extends ApiException {

    public GatewayException(ApiException parent, String operation) {
        super(String.format("Could not execute gateway operation %s due to %s", operation, parent.getMessage()), parent, parent.getCode(), parent.getResponseHeaders());
    }
}
