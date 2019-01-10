package com.example.hugosimon.creationpostebenup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.hugosimon.creationpostebenup.MyRequest.MyRequest;


public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private TextInputLayout til_pseudo, til_password;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // permet d'acceder a la page activity_login


        btn_login = findViewById(R.id.btn_login);

        til_pseudo = findViewById(R.id.til_pseudo);
        til_password = findViewById(R.id.til_password);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);
        btn_login.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                String pseudo = til_pseudo.getEditText().getText().toString().trim();
                String password = til_password.getEditText().getText().toString().trim();

                if (pseudo.length() > 0 && password.length()>0) {

                    request.login(pseudo, password, new MyRequest.LoginCallback() {
                        @Override
                        public void onSuccess(String id, String pseudo) {
                            Intent intent = new Intent(getApplicationContext(),PremiereActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
