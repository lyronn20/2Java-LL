package models;

public class Store {
    private int id;
    private String name;
    private String location;

    public Store(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }


    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}