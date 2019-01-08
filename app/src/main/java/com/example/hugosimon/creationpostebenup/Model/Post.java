package com.example.hugosimon.creationpostebenup.Model;

import java.io.File;
import java.util.Date;

public class Post {
    //propriete
    private long _id;
    private int user_id;
    private File content;
    private String title;
    private String description;
    private Date date_creation;
    private int nb_crying = 0;
    private int nb_love = 0;
    private int nb_think = 0;
    private int nb_yawning = 0;
    private String coiffeur;
    private String yeux;
    private String sourcil;
    private String levre;
    private String teint;
    private String chemin;
    private String type;
    private String extension;
    private int nbLikes;
    private String comment;

    public Post(long id, int userid) {

    }


    /* public getOwner() {

    }*/
}