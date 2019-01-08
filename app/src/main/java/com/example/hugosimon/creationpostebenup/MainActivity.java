package com.example.hugosimon.creationpostebenup;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
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
import java.net.URI;

//import com.ebenup.niru.ebenup.R;

public class MainActivity extends AppCompatActivity {
    private long id;
    private File content;
    private String posterName;
    private int nbLikes;
    public static final int REQUEST_FILE = 1;
    private final int nbData = 10;
    private String contentPath;

    public File getContent() {
        return content;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FILE) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                if(ctrl.accept(new File(getRealPathFromURI(imageUri)))){
                    content = new File(getRealPathFromURI(imageUri));
                    contentPath = getRealPathFromURI(imageUri);
                    try {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
    }
    private TextView txtUsername;
    private EditText txtDesc;
    private ImageView imgContent;
    private ImageView imgPP;
    private EditText txtCoif;
    private EditText txtYeux;
    private EditText txtSourcils;
    private EditText txtLevres;
    private EditText txtTeint;
    private Button btnPublier;
    private EditText txtTitre;
    private Controller_enregistrementPost ctrl = Controller_enregistrementPost.getInstance();
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
        //setPP();
        ecoutePublier();
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

    /*private void setPP(){
        if(){

        }
        else{
            imgPP.setImageResource(R.drawable.pp_vide);
        }
    }*/
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
