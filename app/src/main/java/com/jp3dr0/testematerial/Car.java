package com.jp3dr0.testematerial;

/**
 * Created by joaop on 15/11/2017.
 */

public class Car {
    private String model;
    private String brand;
    private int photo;
    private boolean ehFavorito = false;

    public Car(){}

    public Car(String model, String brand, int photo) {
        this.model = model;
        this.brand = brand;
        this.photo = photo;
    }

    public boolean isEhFavorito() {
        return ehFavorito;
    }

    public void setEhFavorito(boolean ehFavorito) {
        this.ehFavorito = ehFavorito;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
