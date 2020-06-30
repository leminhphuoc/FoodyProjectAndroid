package hcmute.edu.vn.nhom02.foodyproject.model;

public class CategoryFood {
    private int Id;
    private String Name;

    public CategoryFood(){

    }

    public CategoryFood(int id, String name) {
        Id = id;
        Name = name;
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
}
