package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class adminActivity extends AppCompatActivity {
    RecyclerView rec;
    adapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        rec=findViewById(R.id.recycler);
        LinearLayoutManager ln=new LinearLayoutManager(this);
        rec.setLayoutManager(ln);
        adp=new adapter(this);
        rec.setAdapter(adp);
        cargar();
    }

    private void cargar() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get("http://192.168.43.132:8000/user",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject ob=response.getJSONObject(i);
                        String nom=ob.getString("nombre");
                        String id=ob.getString("_id");
                        item it=new item(nom,id);
                        adp.add(it);
                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
