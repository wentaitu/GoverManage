package com.nexuslink.govermanage.bean;

public class EventModel {

    public EventModel() {
    }

    public EventModel(String status, int id, String title, int emergency, int source, String time, String content,int type) {
        this.status = status;
        this.id = id;
        this.title = title;
        this.emergency = emergency;  // 0-1
        this.source = source;  // 0-2
        this.time = time;
        this.content = content;
        this.type = type; //0-4
    }

    String status;
    int id;
    String title;
    int emergency;
    int source;
    String time;
    String content;
    String imgUrl;
    int type;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEmergency() {
        return emergency;
    }

    public void setEmergency(int emergency) {
        this.emergency = emergency;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
