package com.example.hugosimon.creationpostebenup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.hugosimon.creationpostebenup.MyRequest.MyRequest;

import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private Button btn_send;
    private TextInputLayout til_prenom,til_adresse,til_pseudo,til_email,til_password,til_password2;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btn_send = (Button) findViewById(R.id.btn_send);
        til_prenom = (TextInputLayout) findViewById(R.id.til_prenom);
        til_adresse = (TextInputLayout) findViewById(R.id.til_adresse);
        til_pseudo = (TextInputLayout) findViewById(R.id.til_pseudo);
        til_email = (TextInputLayout) findViewById(R.id.til_email);
        til_password = (TextInputLayout) findViewById(R.id.til_password);
        til_password2 = (TextInputLayout) findViewById(R.id.til_password2);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        btn_send.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                String prenom = til_prenom.getEditText().getText().toString().trim();
                String adresse = til_adresse.getEditText().getText().toString().trim();
                String pseudo = til_pseudo.getEditText().getText().toString().trim();
                String email = til_email.getEditText().getText().toString().trim();
                String password = til_password.getEditText().getText().toString().trim();
                String password2 = til_password2.getEditText().getText().toString().trim();


                if (prenom.length() > 0 && adresse.length()>0 && pseudo.length()>0 && email.length()> 0 && password.length()>0 && password2.length()>0) {
                    request.register(prenom, adresse, pseudo, email, password, password2, new MyRequest.RegisterCallback() {
                        @Override
                        public void onSuccess(String message) {
                            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                            intent.putExtra("REGISTER", message);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void inputErrors(Map<String, String> errors) {

                            if (errors.get("prenom") != null) {
                                til_prenom.setError(errors.get("prenom"));
                            } else {
                                til_prenom.setEnabled(false);
                            }
                                if (errors.get("adresse") != null) {
                                    til_adresse.setError(errors.get("adresse"));
                                } else {
                                    til_adresse.setEnabled(false);
                                }
                                    if (errors.get("pseudo") != null) {
                                        til_pseudo.setError(errors.get("pseudo"));
                                    } else {
                                        til_pseudo.setEnabled(false);
                                    }
                                    if (errors.get("email") != null) {
                                        til_email.setError(errors.get("email"));
                                    } else {
                                        til_email.setEnabled(false);
                                    }
                                    if (errors.get("password") != null) {
                                        til_password.setError(errors.get("password"));
                                    } else {
                                        til_password2.setEnabled(false);
                                    }
                                }

                                @Override
                                public void onError (String message){
                                    Log.d("ppppp", message + "*****");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else

                        {
                            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }




}