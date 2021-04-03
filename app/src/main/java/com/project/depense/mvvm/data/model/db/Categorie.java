package com.project.depense.mvvm.data.model.db;

import com.google.firebase.firestore.PropertyName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorie")
public class Categorie {

    @PrimaryKey()
    @PropertyName("id")
    private Long id;

    @PropertyName("libelle")
    private String libelle;

    public Categorie() {
    }

    public Categorie(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
