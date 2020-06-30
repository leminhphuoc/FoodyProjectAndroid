package hcmute.edu.vn.nhom02.foodyproject.model;

public class RestaurantImage {
    private  int Id;
    private String Source;
    private int RestaurantId;

    public RestaurantImage(){

    }

    public RestaurantImage(int id, String source, int restaurantId) {
        Id = id;
        Source = source;
        RestaurantId = restaurantId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public int getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        RestaurantId = restaurantId;
    }
}
