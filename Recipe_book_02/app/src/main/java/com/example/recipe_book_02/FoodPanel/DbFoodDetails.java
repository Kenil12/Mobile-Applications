package com.example.recipe_book_02.FoodPanel;

public class DbFoodDetails {
    String dishName, dishPrice, dishDescription, RanDomUID, ChefUID, ImageURL;

    public DbFoodDetails() {
    }

    public DbFoodDetails(String dishName, String dishPrice, String dishDescription, String ranDomUID, String chefUID, String imageURL) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishDescription = dishDescription;
        RanDomUID = ranDomUID;
        ChefUID = chefUID;
        ImageURL = imageURL;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public String getRanDomUID() {
        return RanDomUID;
    }

    public void setRanDomUID(String ranDomUID) {
        RanDomUID = ranDomUID;
    }

    public String getChefUID() {
        return ChefUID;
    }

    public void setChefUID(String chefUID) {
        ChefUID = chefUID;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
