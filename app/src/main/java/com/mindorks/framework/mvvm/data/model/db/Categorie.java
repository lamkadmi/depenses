package com.mindorks.framework.mvvm.data.model.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorie")
public class Categorie {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String libelle;

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

    @Override
    public String toString() {
        return libelle;
    }
}
