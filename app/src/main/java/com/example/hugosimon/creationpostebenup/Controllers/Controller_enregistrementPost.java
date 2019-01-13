package com.example.hugosimon.creationpostebenup.Controllers;

import com.example.hugosimon.creationpostebenup.Model.AccesDistant;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Simon
 * Controleur qui va verifier que les posts sont au bon format et les enregistrer dans la bdd
 */

public class Controller_enregistrementPost {

    /**
     *  Instance du controlleur etant donne qu'il s'agit d'un singleton
     */

    private static Controller_enregistrementPost instance = null;

    /**
     *  Objet acces distant permettant la connexion à la bdd
     */

    private static AccesDistant accesDistant;

    /**
     *  id de l'utilisateur connecte pour faire le lien entre celui ci et le post
     */

    private int user_id;

    /**
     *  Constructeur, comme il n'a aucune particularites on met appelle juste le superconstructeur
     */

    private Controller_enregistrementPost(){
        super();
    }

    /**
     *  Liste des extensions de fichiers acceptes
     */

    private final String[] okFileExtensions =  new String[] {"jpg", "png","jpeg", "gif"};

    /**
     * verifie que le fichier est dans un format accepte
     * @param file fichier a tester
     * @return true si accepte false sinon
     */

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

    /**
     *  Methode qui va regarder si une instance du controleur est declaree et en declarer une si ce n'est pas le cas
     *  elle sert principalement de construteur
     *  @return l'instance du controleur
     */

    public static final Controller_enregistrementPost getInstance(){
        if(Controller_enregistrementPost.instance == null){
            Controller_enregistrementPost.instance = new Controller_enregistrementPost();
            accesDistant = new AccesDistant();
            accesDistant.envoi("currentUserId", new JSONArray());
        }
        return Controller_enregistrementPost.instance;
    }

    /**
     *  Verifie que les champs ne soient pas vides
     *  @param data informations des champs a tester
     *  @return true si les champs ne sont pas vide, false sinon
     */

    public boolean estValide(String[]data){
        for (int i = 0; i < data.length; i++){
            if(data[i] == null){
                return false;
            }
        }
        return true;
    }

    /**
     *  methode qui va envoier a acces distant les informations a sauvegarder
     *  @param userid   identifiant de l'utilisateur courant
     *  @param textData donnees a enregistrer
     */

    public void publierPost(int userid, String[] textData){

        accesDistant.envoi("publierPost",this.convertToJSONArray(user_id, textData));
    }

    /**
     * Methode qui va convertir les donnees java en tableau JSON
     * @param user_id identifiant de l'utilisateur courant
     * @param textData donnees a enregistrer
     * @return le tableau JSON
     */

    public JSONArray convertToJSONArray(int user_id, String[] textData){
        List laListe = new ArrayList();
        laListe.add(user_id);
        for(int i = 0; i < textData.length; i++){
            laListe.add(textData[i]);
        }
        return new JSONArray(laListe);
    }
}
