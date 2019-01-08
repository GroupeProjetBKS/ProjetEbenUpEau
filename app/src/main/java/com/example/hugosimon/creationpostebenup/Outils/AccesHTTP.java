package com.example.hugosimon.creationpostebenup.Outils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.hugosimon.creationpostebenup.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.util.ArrayList;

public class AccesHTTP extends AsyncTask<String, Integer, Long>{
    private ArrayList<NameValuePair> parametres;
    public AsyncResponse delegate = null;
    private String ret = null;

    public AccesHTTP(){
        parametres = new ArrayList<NameValuePair>();
    }

    public void addParam(String nom, String valeur){
        parametres.add(new BasicNameValuePair(nom, valeur));
        Log.d("params",parametres.toString() + "*****");
    }
    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHttp = new DefaultHttpClient();
        HttpPost paramcnx = new HttpPost(strings[0]);

        try {
            paramcnx.setEntity(new UrlEncodedFormEntity(parametres));
            HttpResponse reponse = cnxHttp.execute(paramcnx);
            ret = EntityUtils.toString(reponse.getEntity());
            Log.d("puteputepute",ret + "*****ttt");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Long result){
        delegate.processFinish(ret.toString());
    }
}
