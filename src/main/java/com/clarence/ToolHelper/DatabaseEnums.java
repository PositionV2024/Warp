package com.clarence.ToolHelper;

public enum DatabaseEnums {
    TITLE("title");
    private String name;
    DatabaseEnums(String name) {
        this.name = name;
    }
    public String getName() {return name;}
}
