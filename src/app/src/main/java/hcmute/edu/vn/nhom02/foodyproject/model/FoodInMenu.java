package hcmute.edu.vn.nhom02.foodyproject.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class FoodInMenu implements Serializable {
    private String FoodName;
    private Double Price;

    public FoodInMenu(String foodName, Double price) {
        FoodName = foodName;
        Price = price;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return FoodName + "\n" + Price;
    }
}
