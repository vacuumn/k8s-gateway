package com.hazelcast.k8s.gateway.dto;

public class ListDeploymentRequest {

    public String _continue = null;
    public String fieldSelector = null;
    public String labelSelector = null;
    public Integer limit = null;
    public String pretty = null;
    public String resourceVersion = null;
    public Integer timeoutSeconds = null;
    public Boolean watch = null;
    public Boolean include = null;

    @Override
    public String toString() {
        return "ListDeploymentRequest{" +
                "_continue='" + _continue + '\'' +
                ", fieldSelector='" + fieldSelector + '\'' +
                ", labelSelector='" + labelSelector + '\'' +
                ", limit=" + limit +
                ", pretty='" + pretty + '\'' +
                ", resourceVersion='" + resourceVersion + '\'' +
                ", timeoutSeconds=" + timeoutSeconds +
                ", watch=" + watch +
                ", include=" + include +
                '}';
    }
}
