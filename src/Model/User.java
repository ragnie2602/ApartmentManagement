package Model;

import javax.swing.ImageIcon;

public class User {
    private int id;
    private String name;
    private String birthday;
    private String phoneNumber;
    private ImageIcon img;
    private String address;

    public User() {}
    public User(int id, String name, String birthday, String phoneNumber, ImageIcon img, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.img = img;
        this.address = address;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getBirthday() {return birthday;}
    public ImageIcon getImg() {return img;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getAddress() {return address;}

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setBirthday(String birthday) {this.birthday = birthday;}
    public void setImg(ImageIcon img) {this.img = img;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public void setAddress(String address) {this.address = address;}
}
