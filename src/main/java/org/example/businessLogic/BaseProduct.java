package org.example.businessLogic;

import org.example.dataAccess.Serializator;

import java.io.Serializable;

public class BaseProduct extends MenuItem implements Serializable {
    private String title;
    private Double rating;
    private Integer calories;
    private Integer protein;
    private Integer fat;
    private Integer sodium;
    private Integer price;

    public BaseProduct(String title, Double rating, Integer calories, Integer protein, Integer fat, Integer sodium, Integer price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Double getRating() {
        return rating;
    }

    @Override
    public Integer getCalories() {
        return calories;
    }

    @Override
    public Integer getProtein() {
        return protein;
    }

    @Override
    public Integer getFat() {
        return fat;
    }

    @Override
    public Integer getSodium() {
        return sodium;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public Integer computePrice() {
        return price;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", rating=" + rating +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", price=" + price +
                '\n';
    }
}
