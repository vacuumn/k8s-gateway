package com.hazelcast.k8s.gateway.repository;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class DeploymentEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ElementCollection
    private List<String> images;

    public DeploymentEntity(String name, List<String> images) {
        this.name = name;
        this.images = images;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}