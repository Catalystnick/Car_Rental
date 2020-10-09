package com.geekdroids.carrental;

public class Employee {
    private String Name;
    private String DOB;
    private String NIC;
    private String Phone;
    private String post;
    private String Email;

    public Employee() {

    }

    public Employee(String name, String DOB, String NIC, String phone, String post, String email) {
        Name = name;
        this.DOB = DOB;
        this.NIC = NIC;
        Phone = phone;
        this.post = post;
        Email = email;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
