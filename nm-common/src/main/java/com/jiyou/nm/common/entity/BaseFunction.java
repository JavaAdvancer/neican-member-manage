package com.jiyou.nm.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by will on 2015/11/24.
 */
public class BaseFunction implements Serializable {
    private String id;
    private String name;
    private String key;
    private String url;
    private boolean isActive;
    private List<BaseFunction> functionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<BaseFunction> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<BaseFunction> functionList) {
        this.functionList = functionList;
    }
}
