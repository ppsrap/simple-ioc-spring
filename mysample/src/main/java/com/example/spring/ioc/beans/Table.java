package com.example.spring.ioc.beans;

public class Table {
    String id;
    String tableName;
    String description;

    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                ", tableName='" + tableName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
