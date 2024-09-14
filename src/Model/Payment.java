package Model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Payment {
    private int id;
    private int floor;
    private int room;
    private int feeId;
    private String payee;
    private int quantity;
    private Date timeValidate;
    private int month;
    private int year;
    private long paid;

    public Payment() {}
    public Payment(int floor, int room, int feeId, String payee, int quantity, Date timeValidate, long paid) {
        this.floor = floor;
        this.room = room;
        this.feeId = feeId;
        this.payee = payee;
        this.quantity = quantity;
        this.timeValidate = timeValidate;
        this.month = 0;
        this.year = 0;
        this.paid = paid;
    }
    public Payment(int floor, int room, int feeId, String payee, int quantity, Date timeValidate, int month, int year, long paid) {
        this.floor = floor;
        this.room = room;
        this.feeId = feeId;
        this.payee = payee;
        this.quantity = quantity;
        this.timeValidate = timeValidate;
        this.month = month;
        this.year = year;
        this.paid = paid;
    }
    public Payment(int id, int floor, int room, int feeId, String payee, int quantity, Date timeValidate, int month, int year, long paid) {
        this.id = id;
        this.floor = floor;
        this.room = room;
        this.feeId = feeId;
        this.payee = payee;
        this.quantity = quantity;
        this.timeValidate = timeValidate;
        this.month = month;
        this.year = year;
        this.paid = paid;
    }

    public int getId() {return id;}
    public int getFloor() {return floor;}
    public int getRoom() {return room;}
    public int getFeeId() {return feeId;}
    public String getPayee() {return payee;}
    public int getQuantity() {return quantity;}
    public Date getTimeValidate() {return timeValidate;}
    public int getMonth() {return month;}
    public int getYear() {return year;}
    public long getPaid() {return paid;}

    public void setId(int id) {this.id = id;}
    public void setFloor(int floor) {this.floor = floor;}
    public void setRoom(int room) {this.room = room;}
    public void setFeeId(int feeId) {this.feeId = feeId;}
    public void setPayee(String payee) {this.payee = payee;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setTimeValidate(Date timeValidate) {this.timeValidate = timeValidate;}
    public void setMonth(int month) {this.month = month;}
    public void setYear(int year) {this.year = year;}

    public String[] toData() {
        String[] data = {String.valueOf(floor), String.valueOf(room), payee, (new SimpleDateFormat("dd/MM/yyyy")).format(timeValidate), (year == 0 ? "Một lần" : month == 0 ? ("Năm " + year) : ("Tháng " + month + "/" + year)), String.valueOf(quantity), String.valueOf(paid)};
        return data;
    }
}
