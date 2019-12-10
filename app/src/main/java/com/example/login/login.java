package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class login extends AppCompatActivity implements View.OnClickListener {
    EditText email,pass;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        btn=findViewById(R.id.env);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.env){
            cargar();
        }
    }

    private void cargar() {
        AsyncHttpClient clien=new AsyncHttpClient();
        RequestParams req=new RequestParams();
        req.put("email",email.getText().toString());
        req.put("password",pass.getText().toString());
        clien.post("http://192.168.43.132:8000/user/login", req, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                try {
                    String resp=response.getString("message");
                    if(resp.equals("autenticacion exitosa")){
                        if(response.getBoolean("admin")){
                            Intent in=new Intent(login.this,adminActivity.class);
                            utilidades.token=response.getString("token");
                            startActivity(in);
                        }else{
                            Intent in=new Intent(login.this,MainActivity.class);
                            utilidades.token=response.getString("token");
                            startActivity(in);
                        }
                    }else if(resp.equals("el password es incorrecto")){
                        Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }
        });
    }
}
