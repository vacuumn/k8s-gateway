package com.hazelcast.k8s.gateway.dto;


/**
 * DTO for holding list deployments params, more details in README.MD (TODO: add fields description here as well)
 */
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

    public ListDeploymentRequest(){}

    public String get_continue() {
        return _continue;
    }

    public void set_continue(String _continue) {
        this._continue = _continue;
    }

    public String getFieldSelector() {
        return fieldSelector;
    }

    public void setFieldSelector(String fieldSelector) {
        this.fieldSelector = fieldSelector;
    }

    public String getLabelSelector() {
        return labelSelector;
    }

    public void setLabelSelector(String labelSelector) {
        this.labelSelector = labelSelector;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getPretty() {
        return pretty;
    }

    public void setPretty(String pretty) {
        this.pretty = pretty;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public Boolean getWatch() {
        return watch;
    }

    public void setWatch(Boolean watch) {
        this.watch = watch;
    }

    public Boolean getInclude() {
        return include;
    }

    public void setInclude(Boolean include) {
        this.include = include;
    }

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
