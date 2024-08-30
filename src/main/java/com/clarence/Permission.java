package com.clarence;

public enum Permission {
    USEAGE("warp.use");

    private String name;
    Permission(String name) {
        this.name = name;
    }
    public String getPermissionNode() { return  name; }
}
