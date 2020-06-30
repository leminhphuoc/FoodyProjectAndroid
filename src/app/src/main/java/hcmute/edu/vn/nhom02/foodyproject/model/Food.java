package hcmute.edu.vn.nhom02.foodyproject.model;

public class Food {
    private int Id;
    private String Name;
    private Double Price;
    private String Image;
    private int RestaurantId;
    private int FoodCategoryId;

    public Food() {
    }

    public Food(int id, String name, Double price, String image, int restaurantId, int foodCategoryId) {
        Id = id;
        Name = name;
        Price = price;
        Image = image;
        RestaurantId = restaurantId;
        FoodCategoryId = foodCategoryId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        RestaurantId = restaurantId;
    }

    public int getFoodCategoryId() {
        return FoodCategoryId;
    }

    public void setFoodCategoryId(int foodCategoryId) {
        FoodCategoryId = foodCategoryId;
    }
}
