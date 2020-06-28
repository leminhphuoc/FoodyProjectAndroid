package hcmute.edu.vn.nhom02.foodyproject.model;

public class Province {
    private int id;
    private String name;
    private String note;

    public Province() {
    }

    public Province(int id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }

    public Province(String name, String note) {
        this.name = name;
        this.note = note;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNote() {return note;}

    public void setNote(String note) {this.note = note; }

    public Province(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
