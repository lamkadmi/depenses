package com.mindorks.framework.mvvm.data.local.db.dao;

import com.mindorks.framework.mvvm.data.model.db.Depense;
import com.mindorks.framework.mvvm.data.model.db.Prevision;

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
}
