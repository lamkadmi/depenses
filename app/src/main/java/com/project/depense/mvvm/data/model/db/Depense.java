package com.project.depense.mvvm.data.model.db;

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
                )
        }
)
public class Depense {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "categorieId", index = true)
    private Long categorieId;

    private Date depenseDate;

    private Float montant;

    private String detail;

    public Depense(Long categorieId, Date depenseDate, Float montant, String detail) {
        this.categorieId = categorieId;
        this.depenseDate = depenseDate;
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

    public Date getDepenseDate() {
        return depenseDate;
    }

    public void setDepenseDate(Date depenseDate) {
        this.depenseDate = depenseDate;
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

}
