package com.mobileapp.rugshop.model;

public class Carpet {
    private String name;
    private String color;
    private String type;
    private Integer width;
    private Integer length;
    private Integer price;
    private Integer imageResource;

    public Carpet() {
    }

    public Carpet(String name, String color, String type, Integer width, Integer length, Integer price, Integer imageResource) {
        this.name = name;
        this.color = color;
        this.type = type;
        this.width = width;
        this.length = length;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }
}
