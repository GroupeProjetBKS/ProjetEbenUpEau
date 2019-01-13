package com.example.hugosimon.creationpostebenup.Model;

import android.util.Log;

import com.example.hugosimon.creationpostebenup.Controllers.Controller_enregistrementPost;
import com.example.hugosimon.creationpostebenup.Outils.AccesHTTP;
import com.example.hugosimon.creationpostebenup.Outils.AsyncResponse;

import org.json.JSONArray;

/**
 *  @author Hugo SIMON
 *  Classe qui va faire le lien entre le controleur d'enregistrement de post et la bdd
 */

public class AccesDistant implements AsyncResponse {

    /**
     *  adresse du fichier php contenant les requetes de publication
     */

    //a changer par l'adresse du serv ebenup
    private static final String SERVERADDR = "http://192.168.43.83/EbenUp/ServeurEbenUp.php";

    /**
     *  Instance du controleur pour pouvoir utiliser ses methodes
     */

    private Controller_enregistrementPost controle;
    public AccesDistant(){
        controle = Controller_enregistrementPost.getInstance();
    }

    /**
     *  Methode appellee une fois que la requete est terminee
     *  @param output message envoyé
     */

    @Override
    public void processFinish(String output) {
        Log.d("serveur", "*****************");
        String[]message = output.split("%");

        if(message.length>1){
             if (message[0].equals("publierPost")){
                Log.d("publicReussie !", " PUBLICATION ********************");
            }
        }
    }

    /**
     *  Methode qui va envoyer les demandes au serveur
     *  @param operation nom de l'operation voulue
     *  @param lesDonneesJSON donnees a transmettre
     */

    public void envoi(String operation, JSONArray lesDonneesJSON){

        AccesHTTP accesDonnees = new AccesHTTP();

        accesDonnees.delegate = this;
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("data",lesDonneesJSON.toString());
        accesDonnees.execute(SERVERADDR);
    }
}
