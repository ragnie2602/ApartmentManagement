package Model;

public class Vehicle {
    private String licensePlates;
    private int floor;
    private int room;
    private int type;

    public Vehicle(String licensePlates, int floor, int room, int type) {
        this.licensePlates = licensePlates;
        this.floor = floor;
        this.room = room;
        this.type = type;
    }

    public String getLicensePlates() {return licensePlates;}
    public int getFloor() {return floor;}
    public int getRoom() {return room;}
    public int getType() {return type;}

    public void setLicensePlates(String licensePlates) {this.licensePlates = licensePlates;}
    public void setFloor(int floor) {this.floor = floor;}
    public void setRoom(int room) {this.room = room;}
    public void setType(int type) {this.type = type;}

    public String[] toData() {
        String[] data = {String.valueOf(floor), String.valueOf(room), licensePlates, type == 0 ? "Xe máy" : "Ô tô"};
        return data;
    }
}
