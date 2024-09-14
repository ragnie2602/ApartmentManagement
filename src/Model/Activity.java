package Model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Activity {
    private int id;
    private Long residentId;
    private int status;
    private Date timeIn;
    private Date timeOut;
    private String note;

    public Activity() {}
    public Activity(Long residentId, int status, Date timeIn, Date timeOut, String note) {
        this.residentId = residentId;
        this.status = status;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.note = note;
    }
    public Activity(int id, Long residentId, int status, Date timeIn, Date timeOut, String note) {
        this.id = id;
        this.residentId = residentId;
        this.status = status;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.note = note;
    }

    public int getId() {return id;}
    public Long getResidentId() {return residentId;}
    public int getStatus() {return status;}
    public String getStatusStr() {
        switch (status) {
            case 0:
                return "Thường trú";
            case 1:
                return "Tạm trú";
            case 2:
                return "Tạm vắng";
            case 3:
                return "Rời đi";
            case 4:
                return "Chuyển hộ";
            default:
                return "";
        }
    }
    public Date getTimeIn() {return timeIn;}
    public Date getTimeOut() {return timeOut;}
    public String getNote() {return note;}

    public void setId(int id) {this.id = id;}
    public void setResidentId(Long residentId) {this.residentId = residentId;}
    public void setStatus(int status) {this.status = status;}
    public void setTimeIn(Date timeIn) {}
    public void setTimeOut(Date timeOut) {this.timeOut = timeOut;}
    public void setNote(String note) {this.note = note;}
    
    public String[] toData() {
        String[] data = {
            String.valueOf(id),
            getStatusStr(),
            timeIn == null ? "" : (new SimpleDateFormat("dd/MM/yyyy")).format(timeIn),
            timeOut == null ? "" : (new SimpleDateFormat("dd/MM/yyyy")).format(timeOut),
            note
        };
        
        return data;
    }
}
