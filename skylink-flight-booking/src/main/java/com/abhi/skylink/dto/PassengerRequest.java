package com.abhi.skylink.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PassengerRequest {

    @NotBlank(message = "Passenger name is required")
    private String name;

    @Min(value = 1, message = "Age must be at least 1")
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "ID proof type is required")
    private String idProof;

    @NotBlank(message = "ID number is required")
    private String idNumber;

    public PassengerRequest() {}

    // Getters & Setters
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
