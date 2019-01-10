package com.example.hugosimon.creationpostebenup.Model;

import android.util.Log;

import com.example.hugosimon.creationpostebenup.Controllers.Controller_enregistrementPost;
import com.example.hugosimon.creationpostebenup.Outils.AccesHTTP;
import com.example.hugosimon.creationpostebenup.Outils.AsyncResponse;

import org.json.JSONArray;

public class AccesDistant implements AsyncResponse {
    //a changer par l'adresse du serv ebenup
    private static final String SERVERADDR = "http://192.168.43.83/EbenUp/ServeurEbenUp.php";
    private Controller_enregistrementPost controle;
    public AccesDistant(){
        controle = Controller_enregistrementPost.getInstance();
    }
    @Override
    public void processFinish(String output) {
        Log.d("serveur", "*****************");
        String[]message = output.split("%");

        if(message.length>1){
   /*         if(message[0].equals("enreg")){
                Log.d("enreg", "******************"+message[1]);
            }
            else if (message[0].equals("dernier")){
                Log.d("dernier", "*******************");
            }*/
            /*else*/ if (message[0].equals("publierPost")){
                Log.d("Erreur !", " PUBLICATION ********************");
            }
        }
    }

    public void envoi(String operation, JSONArray lesDonneesJSON){

        AccesHTTP accesDonnees = new AccesHTTP();

        accesDonnees.delegate = this;
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("data",lesDonneesJSON.toString());
        accesDonnees.execute(SERVERADDR);
    }
}
