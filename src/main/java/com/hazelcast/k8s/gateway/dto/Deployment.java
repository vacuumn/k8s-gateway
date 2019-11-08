package com.hazelcast.k8s.gateway.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;


public class Deployment {
    @NotEmpty(message = "Name may not be empty")
    @Pattern(regexp="[a-z0-9]([-a-z0-9]*[a-z0-9])")
    private String name;
    @NotEmpty(message = "At least one image should be provided")
    private List<String> images;
    private Map<String, String> labels;

    public Deployment(String name, List<String> images, Map<String, String> labels) {
        this.name = name;
        this.images = images;
        this.labels = labels;
    }

    public Deployment() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}
