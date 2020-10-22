package com.example.petappnewver1910;

import java.time.Instant;
import java.util.Date;

public class Pet {

    public String id, name, sex;
    public int age;
    private long bDay;

    public Pet() {

    }

    public Pet(String name, String sex, Date birthDate) {
        this.name = name;
        this.bDay = birthDate.getTime();
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getbDay() {
        return bDay;
    }

    public void setbDay(long bDay) {
        this.bDay = bDay;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
