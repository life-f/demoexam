package org.demoexam.app.data.entity;

import java.util.Date;

public class PersonEntity {
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String gender;
    private Date birthdate;
    private String place_of_birth;
    private String photo_path;

    public PersonEntity(int id, String surname, String name, String patronymic, String gender, Date birthdate, String place_of_birth, String photo_path) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthdate = birthdate;
        this.place_of_birth = place_of_birth;
        this.photo_path = photo_path;
    }

    public PersonEntity(String surname, String name, String patronymic, String gender, Date birthdate, String place_of_birth, String photo_path) {
        this(-1, surname, name, patronymic, gender, birthdate, place_of_birth, photo_path);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
