package hcmute.edu.vn.nhom02.foodyproject.model;

public class Province {
    private int Id;
    private String Name;
    private String Note;

    public Province() {
    }

    public Province(int id, String name, String note) {
        Id = id;
        Name = name;
        Note = note;
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

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
