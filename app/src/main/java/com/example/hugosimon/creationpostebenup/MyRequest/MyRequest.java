package com.example.hugosimon.creationpostebenup.MyRequest;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class MyRequest {
    private Context context;
    private RequestQueue queue;


    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }


    public void register(final String prenom, final String adresse, final String pseudo, final String email, final String password, final String password2, final RegisterCallback callback){
        String url = "http://192.168.0.30/Projet/register.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Map<String, String> errors = new HashMap<>();

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        callback.onSuccess("Vous êtes bien inscrit");

                    }else{
                        JSONObject message = json.getJSONObject("message");
                        if (message.has("prenom")){
                            message.put("prenom", message.getString("prenom"));
                        }
                        if (message.has("adresse")){
                            message.put("adresse", message.getString("adresse"));
                        }
                        if (message.has("pseudo")){
                            message.put("pseudo", message.getString("pseudo"));
                        }
                        if (message.has("email")){
                            message.put("email", message.getString("email"));
                        }
                        if (message.has("password")){
                            message.put("password", message.getString("password"));
                        }
                        callback.inputErrors(errors);
                    }


                }catch (JSONException e){
                    e.printStackTrace();
                }




            }


        }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callback.onError("Vous n'êtes pas connecter a internet");
                }else if (error instanceof VolleyError)
                    callback.onError("Une erreur s'est produite");
            }
        }){


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("prenom", prenom);
                map.put("adresse", adresse);
                map.put("pseudo", pseudo);
                map.put("email", email);
                map.put("password", password);
                map.put("password2", password2);
                return map;
            }
        };
        queue.add(request);
    }

    public interface RegisterCallback {
        void onSuccess(String message);
        void inputErrors(Map<String, String>errors);
        void onError(String message);
    }


    public void login(final String pseudo, final String password, final LoginCallback callback){
        String url = "http://192.168.0.30/Projet/login.php"; // A Changer
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                JSONObject json = null ;
                try {
                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        String id= json.getString(("id"));
                        String pseudo = json.getString("pseudo");
                        callback.onSuccess(id, pseudo);
                    }else {
                        callback.onError(json.getString("message"));
                    }


                }catch (JSONException e){
                    callback.onError("Une erreur s'est produite");
                    e.printStackTrace();
                }




            }


        }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callback.onError("Vous n'êtes pas connecter a internet");
                }else if (error instanceof VolleyError)
                    callback.onError("Une erreur s'est produite");
            }
        }){


            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<>();

                map.put("pseudo", pseudo);
                map.put("password", password);

                return map;
            }
        };
        queue.add(request);
    }

        public interface LoginCallback {
            void onSuccess(String id, String message);
            void onError(String message);
        }
}
