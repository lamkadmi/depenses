package com.mindorks.framework.mvvm.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "prevision",
        foreignKeys = {
                @ForeignKey(
                        entity = Categorie.class,
                        parentColumns = "id",
                        childColumns = "categorieId"
                )
        })
public class Prevision {

    @PrimaryKey
    private Long id;

    @ColumnInfo(name = "categorieId", index = true)
    private Long categorieId;

    private String mois_annee;

    private Float montant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public String getMois_annee() {
        return mois_annee;
    }

    public void setMois_annee(String mois_annee) {
        this.mois_annee = mois_annee;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }
}
