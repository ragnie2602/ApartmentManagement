package Model;

public class Apartment {
    private int id;
    private int floor, room;
    private float area;
    private String ownerName, ownerPhone;

    public Apartment() {}

    public Apartment(int id, int floor, int room, float area, String ownerName, String ownerPhone) {
        this.id = id;
        this.floor = floor;
        this.room = room;
        this.area = area;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
    }

    public int getId() {return id;}
    public int getFloor() {return floor;}
    public int getRoom() {return room;}
    public float getArea() {return area;}
    public String getOwnerName() {return ownerName;}
    public String getOwnerPhone() {return ownerPhone;}

    public void setId(int id) {this.id = id;}    
    public void setFloor(int floor) {this.floor = floor;}
    public void setRoom(int room) {this.room = room;}
    public void setArea(float area) {this.area = area;}
    public void setOwnerName(String ownerName) {this.ownerName = ownerName;}   
    public void setOwnerPhone(String ownerPhone) {this.ownerPhone = ownerPhone;}

    public String[] toData() {
        String[] data = {String.valueOf(floor), String.valueOf(room), ownerName, ownerPhone, String.valueOf(area)};
        return data;
    }
}