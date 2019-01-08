package com.example.hugosimon.creationpostebenup.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.example.hugosimon.creationpostebenup.Model.AccesDistant;
import com.example.hugosimon.creationpostebenup.Model.Post;
import com.example.hugosimon.creationpostebenup.Model.User;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller_enregistrementPost {
    private static Controller_enregistrementPost instance = null;
    private static AccesDistant accesDistant;
    private Post p;
    private int user_id;

    private Controller_enregistrementPost(){
        super();
    }
    private final String[] okFileExtensions =  new String[] {"jpg", "png","jpeg", "gif"};

    public boolean accept(File file)
    {
        for (String extension : okFileExtensions)
        {
            if (file.getName().toLowerCase().endsWith(extension))
            {
                return true;
            }
        }
        return false;
    }
    public static final Controller_enregistrementPost getInstance(){
        if(Controller_enregistrementPost.instance == null){
            Controller_enregistrementPost.instance = new Controller_enregistrementPost();
            accesDistant = new AccesDistant();
            accesDistant.envoi("currentUserId", new JSONArray());
        }
        return Controller_enregistrementPost.instance;
    }

    public boolean estValide(String[]data){
        for (int i = 0; i < data.length; i++){
            if(data[i] == null){
                return false;
            }
        }
        return true;
    }
    public void publierPost(int userid, String[] textData){

        accesDistant.envoi("publierPost",this.convertToJSONArray(user_id, textData));
    }
    public JSONArray convertToJSONArray(int user_id, String[] textData){
        List laListe = new ArrayList();
        laListe.add(user_id);
        for(int i = 0; i < textData.length; i++){
            laListe.add(textData[i]);
        }
        return new JSONArray(laListe);
    }
}
