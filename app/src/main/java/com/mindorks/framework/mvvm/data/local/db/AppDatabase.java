/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.mindorks.framework.mvvm.data.local.db;

import com.mindorks.framework.mvvm.data.local.db.converters.Converters;
import com.mindorks.framework.mvvm.data.local.db.dao.CategorieDao;
import com.mindorks.framework.mvvm.data.local.db.dao.DepenseDao;
import com.mindorks.framework.mvvm.data.local.db.dao.OptionDao;
import com.mindorks.framework.mvvm.data.local.db.dao.PrevisionDao;
import com.mindorks.framework.mvvm.data.local.db.dao.QuestionDao;
import com.mindorks.framework.mvvm.data.local.db.dao.UserDao;
import com.mindorks.framework.mvvm.data.model.db.Categorie;
import com.mindorks.framework.mvvm.data.model.db.Depense;
import com.mindorks.framework.mvvm.data.model.db.Option;
import com.mindorks.framework.mvvm.data.model.db.Prevision;
import com.mindorks.framework.mvvm.data.model.db.Question;
import com.mindorks.framework.mvvm.data.model.db.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Created by lamkadmi on 17/11/19.
 */

@TypeConverters({Converters.class})
@Database(entities = {User.class, Question.class, Option.class, Categorie.class, Depense.class,
        Prevision.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OptionDao optionDao();

    public abstract QuestionDao questionDao();

    public abstract UserDao userDao();

    public abstract CategorieDao categorieDao();

    public abstract PrevisionDao previsionDao();

    public abstract DepenseDao depenseDao();
}
