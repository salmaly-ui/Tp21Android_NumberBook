 package com.example.phonebookpro;

import com.google.gson.annotations.SerializedName;

public class Personne {

    @SerializedName("ref")
    private int ref;

    @SerializedName("nom")
    private String nom;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("origine")
    private String origine;

    @SerializedName("date_ajout")
    private String date_ajout;

    public Personne() {}

    public Personne(String nom, String telephone) {
        this.nom       = nom;
        this.telephone = telephone;
    }

    public int    getRef()        { return ref; }
    public String getNom()        { return nom; }
    public String getTelephone()  { return telephone; }
    public String getOrigine()    { return origine; }
    public String getDate_ajout() { return date_ajout; }

    public void setRef(int ref)               { this.ref = ref; }
    public void setNom(String nom)             { this.nom = nom; }
    public void setTelephone(String t)         { this.telephone = t; }
    public void setOrigine(String o)           { this.origine = o; }
    public void setDate_ajout(String d)        { this.date_ajout = d; }
}