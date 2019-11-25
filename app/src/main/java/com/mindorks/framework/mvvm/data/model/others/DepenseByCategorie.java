package com.mindorks.framework.mvvm.data.model.others;

public class DepenseByCategorie {

    private String libelle;
    private String categorie;
    private Long montant;
    private String mois_annee;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public String getMois_annee() {
        return mois_annee;
    }

    public void setMois_annee(String mois_annee) {
        this.mois_annee = mois_annee;
    }
}
