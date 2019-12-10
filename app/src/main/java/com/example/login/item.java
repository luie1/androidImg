package com.example.login;

public class item {
    private String nombre;
    private String id;
    item(String n,String id){
        nombre=n;
        this.id=id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }
}
