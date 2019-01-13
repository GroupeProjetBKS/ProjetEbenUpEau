package com.example.hugosimon.creationpostebenup;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugosimon.creationpostebenup.Controllers.Controller_enregistrementPost;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

//import com.ebenup.niru.ebenup.R;

/**
 *  Classe faisant office de vue pour la publication de post, elle va lire les entrées utilisateur pour les envoyer au controleur,
 *  celui ci verifiera si le post est valide et si oui enverra une demande de publication à la bdd
 *  @author Hugo SIMON
 */

public class PublicationPostActivity extends AppCompatActivity {

    /**
     * variable qui va stocker la photo selectionnee par l'utilisateur
     */

    private File content;

    /**
     *  id de la requete envoyee lorsque l'utilisateur clique sur l'image pour en changer
     */

    public static final int REQUEST_FILE = 1;

    /**
     *  nombre de donnees au format texte qui seront envoyees
     */

    private final int nbData = 10;

    /**
     *  chemin du fichier selectionne par l'utilisateur
     */

    private String contentPath;

    /**
     *  Reference sur le nom de l'utilisateur
     */

    private TextView txtUsername;

    /**
     *  reference sur la zone de saisie de la description
     */

    private EditText txtDesc;

    /**
     *  Reference sur l'image qui sera sauvegardee en tant que contenu du post
     */

    private ImageView imgContent;

    /**
     *  reference sur la photo de profil de l'utilisateur courant
     */

    private ImageView imgPP;

    /**
     *  reference sur la zone de saisie correspondant au coiffeur
     */

    private EditText txtCoif;

    /**
     *  reference sur la zone de saisie correspondant au nom de la personne ayant fait les yeux
     */

    private EditText txtYeux;

    /**
     *  reference sur la zone de saisie correspondant au nom de la personne ayant fait les sourcils
     */

    private EditText txtSourcils;

    /**
     *  reference sur la zone de saisie correspondant au nom de la personne ayant fait les levres
     */

    private EditText txtLevres;

    /**
     *  reference sur la zone de saisie correspondant au nom de la personne ayant fait le teint
     */

    private EditText txtTeint;

    /**
     *  reference sur le bouton qui permettra de publier le post
     */

    private Button btnPublier;

    /**
     *  reference sur la zone de saisie correspondant au titre du post
     */

    private EditText txtTitre;

    /**
     *  Instance du controleur permettant la publication de post
     */

    private Controller_enregistrementPost ctrl = Controller_enregistrementPost.getInstance();

    /**
     *  methode qui sera executee lorsque l'utilisateur aura choisi une image
     *  @param requestCode code indiquant quelle activitee est choisie
     *  @param resultCode  code envoye permettant de definir si c'est une erreur ou un succes
     *  @param data donnees transmises
     */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FILE) {
            if (resultCode == RESULT_OK) {

                //recup le chemin de l'image
                Uri imageUri = data.getData();
                InputStream inputStream;

                //si image valide
                if(ctrl.accept(new File(getRealPathFromURI(imageUri)))){

                    //la sauvegarder ainsi que son chemin
                    content = new File(getRealPathFromURI(imageUri));
                    contentPath = getRealPathFromURI(imageUri);
                    try {

                        //met l'image au mileu
                        inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bm = BitmapFactory.decodeStream(inputStream);
                        imgContent.setImageBitmap(bm);
                    } catch (FileNotFoundException fnfe) {
                        fnfe.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Format non valide", Toast.LENGTH_LONG);
                }
            }
        }
    }

    /**
     * methode pour recuperer le chemin de l'image
     * @param contentURI chemin "virtuel" de l'image
     * @return le chemin sous forme de chaine
     */

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * Methode appellee au chargement de l'activitee
     * @param savedInstanceState parametre de base pour le demarage d'une activitee
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicationpost);
        this.init();
    }

    /**
     *  initialise chaqu'une des variables d'instance et applique un ontouchlistener sur l'image
     */

    private void init(){
        txtUsername = (TextView)findViewById(R.id.txtUsername);
        txtDesc = (EditText)findViewById(R.id.txtCommentaire);
        imgContent = (ImageView)findViewById(R.id.imgContent);
        imgPP = (ImageView)findViewById(R.id.imgPP);
        txtTitre = (EditText)findViewById(R.id.txtTitre);
        txtCoif = (EditText)findViewById(R.id.txtCoif);
        txtYeux = (EditText)findViewById(R.id.txtYeux);
        txtSourcils = (EditText)findViewById(R.id.txtSourcils);
        txtLevres = (EditText)findViewById(R.id.txtLevres);
        txtTeint = (EditText) findViewById(R.id.txtTeint);
        imgContent.setImageResource(R.drawable.sample_background);
        ecoutePublier();
        imgPP.setImageResource(R.drawable.pp_vide);
        imgContent.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_FILE);
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        return true;
                    }
                }
                return true;
            }
        });
    }

    /**
     *  methode qui va reagir lorsque l'utilisateur appuiera sur le bouton publier, elle va
     *  premierement recuperer les informations saisies par l'utilisateur et demande au controleur de publier le post
     */

    private void ecoutePublier(){
        btnPublier = (Button) findViewById(R.id.btnPublier);
        btnPublier.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    int user_id = 10;
                    String[] textData = new String[nbData];
                    textData[0] = txtTitre.getText().toString();
                    textData[1] = txtDesc.getText().toString();
                    textData[2] = txtCoif.getText().toString();
                    textData[3] = txtYeux.getText().toString();
                    textData[4] = txtSourcils.getText().toString();
                    textData[5] = txtLevres.getText().toString();
                    textData[6] = txtTeint.getText().toString();
                    textData[7] = contentPath;
                    textData[8] = "image";
                    textData[9] = contentPath.substring(contentPath.lastIndexOf("."));
                    Log.d("Erreur !", textData[0] + "\n" + textData[1] + "\n" + textData[2] + "\n"  + textData[3] + "\n" + textData[4] + "\n" + textData[5] + "\n" + textData[6] + "\n" + textData[7] + "\n" + textData[8] + "\n" + textData[9] + "\n" + "*****" );
                    if(ctrl.estValide(textData)) {
                        ctrl.publierPost(user_id, textData);
                       // Toast.makeText(getApplicationContext(), "champs remplis", Toast.LENGTH_LONG).show();

                    }
                    else
                        Toast.makeText(getApplicationContext(),"champs manquants", Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"ayksaipsion" + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
