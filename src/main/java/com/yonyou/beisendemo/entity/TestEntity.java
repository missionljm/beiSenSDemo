package com.yonyou.beisendemo.entity;

public class TestEntity {

    private String id ;
    private String name ;
    private String message;

    public TestEntity() {
    }

    public TestEntity(String id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
