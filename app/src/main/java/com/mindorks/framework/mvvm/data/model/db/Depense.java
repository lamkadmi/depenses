package com.mindorks.framework.mvvm.data.model.db;

import java.math.BigDecimal;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "depense",
        foreignKeys = {
                @ForeignKey(
                        entity = Categorie.class,
                        parentColumns = "id",
                        childColumns = "categorieId"
                ),
                @ForeignKey(
                        entity = Prevision.class,
                        parentColumns = "id",
                        childColumns = "categorieId"
                )
        }
)
public class Depense {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "categorieId", index = true)
    private Long categorieId;

    @ColumnInfo(name = "previsionId", index = true)
    private Long previsionId;

    private Date mois;

    private String mois_annee;

    private Float montant;

    private String detail;

    public Depense(Long categorieId, Long previsionId, String mois_annee, Float montant, String detail) {
        this.categorieId = categorieId;
        this.previsionId = previsionId;
        this.mois_annee = mois_annee;
        this.montant = montant;
        this.detail = detail;
    }

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

    public Long getPrevisionId() {
        return previsionId;
    }

    public void setPrevisionId(Long previsionId) {
        this.previsionId = previsionId;
    }

    public Date getMois() {
        return mois;
    }

    public void setMois(Date mois) {
        this.mois = mois;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMois_annee() {
        return mois_annee;
    }

    public void setMois_annee(String mois_annee) {
        this.mois_annee = mois_annee;
    }
}
