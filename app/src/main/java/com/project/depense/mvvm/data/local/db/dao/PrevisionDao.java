package com.project.depense.mvvm.data.local.db.dao;

import com.project.depense.mvvm.data.model.db.Prevision;
import com.project.depense.mvvm.data.model.others.PrevisionByCategorie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface PrevisionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Prevision categorie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Prevision> previsions);

    @Query("SELECT * FROM prevision")
    Single<List<Prevision>> loadAll();

    @Query("SELECT prevision.id,categorie.libelle as categorie,prevision.mois_annee as date,prevision.montant " +
            "FROM prevision,categorie where categorie.id=prevision.categorieId and prevision.mois_annee= :date")
    Single<List<PrevisionByCategorie>> loadPrevisionsByDate(String date);
}
