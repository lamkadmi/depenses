package com.project.depense.mvvm.data.local.db.dao;

import com.project.depense.mvvm.data.model.db.Revenu;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface RevenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Revenu revenu);

    @Query("SELECT * FROM revenu order by mois_annee")
    Single<List<Revenu>> loadAll();
}
