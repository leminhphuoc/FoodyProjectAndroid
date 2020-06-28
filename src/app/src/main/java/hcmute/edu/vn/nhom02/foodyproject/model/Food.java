package hcmute.edu.vn.nhom02.foodyproject;

public class Food {
    private  String Name;
    private  Double Price;
    private  int Image;

    public Food(String name, Double price, int image) {
        Name = name;
        Price = price;
        Image = image;
    }

    public Food(String name, int image) {
        Name = name;
        Image = image;
    }

    public Food(String name, double price) {
        Name = name;
        Price = price;
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

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String toString() {
        return Name;
    }
}
