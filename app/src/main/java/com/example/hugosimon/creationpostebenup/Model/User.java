package com.example.hugosimon.creationpostebenup.Model;

import java.io.File;

public class User {
    String pseudo;
    String description;
    File pp = null;
    int age;
    boolean sexe;
    int taille;

    public User(String pse, String desc, File pp, int age, boolean sexe, int taille){
        this.pseudo = pse;
        this.description = desc;
        this.pp = pp;
        this.age = age;
        this.sexe = sexe;
        this.taille = taille;
    }
}
