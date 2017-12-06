package com.jp3dr0.testematerial;

/**
 * Created by joaop on 15/11/2017.
 */

public class ItemModel {

    private String name;
    private String imagePath;
    private int photo;

    public ItemModel(){

    }

    public ItemModel(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public ItemModel(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}