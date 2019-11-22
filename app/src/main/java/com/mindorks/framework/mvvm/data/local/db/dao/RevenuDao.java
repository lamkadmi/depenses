package com.mindorks.framework.mvvm.data.local.db.dao;

import com.mindorks.framework.mvvm.data.model.db.Revenu;

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

    @Query("SELECT * FROM revenu")
    Single<List<Revenu>> loadAll();
}
