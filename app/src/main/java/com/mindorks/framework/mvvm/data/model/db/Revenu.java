package com.mindorks.framework.mvvm.data.model.db;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "revenu")
public class Revenu {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String mois_annee;
    private Date date;
    private Float montant;
    private String description;

    public Revenu(String mois_annee, Float montant, String description) {
        this.mois_annee = mois_annee;
         this.montant = montant;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMois_annee() {
        return mois_annee;
    }

    public void setMois_annee(String mois_annee) {
        this.mois_annee = mois_annee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
