package com.example.hugosimon.creationpostebenup.Controllers;

import com.example.hugosimon.creationpostebenup.Model.User;

import java.io.File;

public final class Controller_enregistrementUser {
    private static Controller_enregistrementUser instance = null;
    private User user;

    private Controller_enregistrementUser(){
        super();
    }

    public static final Controller_enregistrementUser getInstance(){
        if(Controller_enregistrementUser.instance == null){
            Controller_enregistrementUser.instance = new Controller_enregistrementUser();
        }
            return Controller_enregistrementUser.instance;
    }

    public void createUser(String pseudo, String description, File pp, int age, boolean sexe, int taille){
        user = new User(pseudo, description, pp, age, sexe, taille);
    }
}
