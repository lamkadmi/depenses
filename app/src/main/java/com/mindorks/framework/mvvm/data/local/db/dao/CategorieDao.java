package com.mindorks.framework.mvvm.data.local.db.dao;

import com.mindorks.framework.mvvm.data.model.db.Categorie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface CategorieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Categorie categorie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Categorie> categories);

    @Query("SELECT * FROM options")
    Single<List<Categorie>> loadAll();
}
