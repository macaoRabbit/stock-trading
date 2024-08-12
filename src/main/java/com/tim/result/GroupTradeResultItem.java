package com.tim.result;

public class GroupTradeResultItem {
    String name;
    String value;
    ReturnItemType type;

    public GroupTradeResultItem(String name, String value, ReturnItemType type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ReturnItemType getType() {
        return type;
    }

    public void setType(ReturnItemType type) {
        this.type = type;
    }
}
