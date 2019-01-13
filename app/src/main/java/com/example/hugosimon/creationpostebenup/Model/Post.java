package com.example.hugosimon.creationpostebenup.Model;

import java.io.File;
import java.util.Date;

/**
 *  classe permettant d'instancier un post
 * @author Hugo SIMON
 */

public class Post {

    /**
     *  identifiant du post
     */

    private long id;

    /**
     *  identifiant de l'utilisateur ayant publie ce post
     */

    private int user_id;

    /**
     *  contenu du post
     */

    private File content;

    /**
     *  titre du post
     */

    private String title;

    /**
     *  description du post
     */

    private String description;

    /**
     *  date de publication
     */

    private Date date_creation;

    /**
     *  nombre d'emoji qui pleurent
     */

    private int nb_crying = 0;

    /**
     *  nombre d'emoji love
     */

    private int nb_love = 0;

    /**
     *  nombre d'emoji think
     */

    private int nb_think = 0;

    /**
     *  nombre d'emoji yawn
     */

    private int nb_yawning = 0;

    /**
     *  nom du coiffeur
     */

    private String coiffeur;

    /**
     *  nom du professionel des yeux
     */

    private String yeux;

    /**
     *  nom du professionel des sourcils
     */

    private String sourcil;

    /**
     *  nom du professionel des levress
     */

    private String levre;

    /**
     *  nom du professionel du teint
     */

    private String teint;

    /**
     *  chemin de l'image dans le telephone
     */

    private String chemin;

    /**
     *  type de fichier
     */

    private String type;

    /**
     *  extension de fichier
     */

    private String extension;

    /**
     *  nombre de likes du post
     */

    private int nbLikes;

    /**
     *  Liste des commentaires du post
     */

    private String[] comment;

    /**
     *  recuperation du post via son id
     */

    public Post(long id) {
        /*JSONArray dataPost = Controller_affichagePost.getPost(id);
        List data = JSONdecode(dataPost);
        */
    }
}