package com.example.petappnewver1910;

public class User {

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumOfPets() {
        return numOfPets;
    }

    public void setNumOfPets(int numOfPets) {
        this.numOfPets = numOfPets;
    }

    public int getNumOfFriends() {
        return numOfFriends;
    }

    public void setNumOfFriends(int numOfFriends) {
        this.numOfFriends = numOfFriends;
    }

    public String fullName, email;
    public int numOfPets, numOfFriends;

    public User(){

    }

    public User(String fullName, String email){
        this.fullName=fullName;
        this.email=email;
        this.numOfPets=0;
        this.numOfFriends=0;
    }

}
