package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class adapter extends RecyclerView.Adapter<adapter.vistas>{
    Context contex;
    ArrayList<item> list;
    adapter(Context c){
        contex=c;
        list=new ArrayList<>();
    }

    void add(item it){
        list.add(it);
        notifyItemChanged(list.indexOf(it));
    }

    void dele(item it){
        int position = list.indexOf(it);

        if(position != -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }

    }

    @NonNull
    @Override
    public vistas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        vistas vh = new vistas(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull vistas h, final int position) {
        String nombre=list.get(position).getNombre();
        h.txt.setText(nombre);
        h.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar(list.get(position).getId());
                list.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    private void eliminar(String id) {
        AsyncHttpClient cli=new AsyncHttpClient();
        cli.delete("http://192.168.43.132:8000/user/"+id,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String msg=response.getString("message");
                    Toast.makeText(contex,""+msg,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class vistas extends RecyclerView.ViewHolder{
        TextView txt;
        Button btn;
        public vistas(@NonNull View view) {
            super(view);
            txt=view.findViewById(R.id.user);
            btn=view.findViewById(R.id.eliminar);
        }
    }
}
