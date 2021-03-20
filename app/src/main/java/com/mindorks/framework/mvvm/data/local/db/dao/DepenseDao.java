package com.mindorks.framework.mvvm.data.local.db.dao;

import com.mindorks.framework.mvvm.data.model.db.Depense;
import com.mindorks.framework.mvvm.data.model.others.DepenseByCategorie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface DepenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Depense categorie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Depense> depenses);

    @Query("SELECT d.detail as libelle,d.montant,d.depenseDate,c.libelle as categorie " +
            "FROM depense d, categorie c where d.categorieId=c.id")
    Single<List<DepenseByCategorie>> loadAll();

    @Query("select categorie.id, categorie.libelle categorie, sum(depense.montant) montant, depense.depenseDate date from categorie, depense " +
            "where depense.categorieId=categorie.id group by categorie.libelle")
    Single<List<DepenseByCategorie>> loadDepensesByCategories();
}
