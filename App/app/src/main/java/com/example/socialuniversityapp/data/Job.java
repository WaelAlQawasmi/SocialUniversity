package com.example.socialuniversityapp.data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "Job")
public class Job {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String title;
    private String body;
    private String phone;
    private String address;

    public Job(String title, String body, String phone, String address) {
        this.title = title;
        this.body = body;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
