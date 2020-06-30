package hcmute.edu.vn.nhom02.foodyproject.model;

public class Tag {
    private int Id;
    private String Name;

    public Tag() {
    }

    public Tag(int id, String name) {
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
