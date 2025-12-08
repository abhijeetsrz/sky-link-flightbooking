package com.abhi.skylink.model;

public class Passenger {

    private Long id;
    private Long bookingId;
    private String name;
    private int age;
    private String gender;
    private String idProof;
    private String idNumber;

    public Passenger() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getIdProof() { return idProof; }
    public void setIdProof(String idProof) { this.idProof = idProof; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
}
