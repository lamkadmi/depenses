package com.mindorks.framework.mvvm.data.local.db.dao;

import com.mindorks.framework.mvvm.data.model.db.Depense;

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

    @Query("SELECT * FROM depense")
    Single<List<Depense>> loadAll();
}