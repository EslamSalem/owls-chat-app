package com.example.owldatabase.Modle;

public class OwlMassage {
    private int massageId;
    private String text;
    private String subject;
    private int fromUser;
    private int toUser;
    private String massageDate;

    public OwlMassage() {
    }

    public OwlMassage(int massageId, String text, int fromUser, int toUser, String massageDate,String subject) {
        this.massageId = massageId;
        this.text = text;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.massageDate = massageDate;
        this.subject = subject;
    }

    public OwlMassage( String text, int fromUser, int toUser, String massageDate ,String subject) {
        this.text = text;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.massageDate = massageDate;
        this.subject = subject;
    }

    public int getMassageId() {
        return massageId;
    }

    public void setMassageId(int massageId) {
        this.massageId = massageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public String getMassageDate() {
        return massageDate;
    }

    public void setMassageDate(String massageDate) {
        this.massageDate = massageDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
