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

package com.project.depense.mvvm.data.local.db;

import com.project.depense.mvvm.data.model.db.Categorie;
import com.project.depense.mvvm.data.model.db.Depense;
import com.project.depense.mvvm.data.model.db.Option;
import com.project.depense.mvvm.data.model.db.Prevision;
import com.project.depense.mvvm.data.model.db.Question;
import com.project.depense.mvvm.data.model.db.Revenu;
import com.project.depense.mvvm.data.model.db.User;
import com.project.depense.mvvm.data.model.others.DepenseByCategorie;
import com.project.depense.mvvm.data.model.others.PrevisionByCategorie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by lamkadmi on 17/11/19.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mAppDatabase.questionDao().loadAll()
                .toObservable();
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(() -> mAppDatabase.userDao().loadAll());
    }

    @Override
    public Observable<List<Option>> getOptionsForQuestionId(final Long questionId) {
        return mAppDatabase.optionDao().loadAllByQuestionId(questionId)
                .toObservable();
    }

    @Override
    public Observable<List<DepenseByCategorie>> getDepenseByCategories() {
        return mAppDatabase.depenseDao().loadDepensesByCategories()
                .toObservable();
    }

    @Override
    public Observable<List<PrevisionByCategorie>> getPrevisionsByDate(String date) {
        return mAppDatabase.previsionDao().loadPrevisionsByDate(date).toObservable();
    }

    @Override
    public Observable<Boolean> insertUser(final User user) {
        return Observable.fromCallable(() -> {
            mAppDatabase.userDao().insert(user);
            return true;
        });
    }

    @Override
    public Observable<Boolean> savePrevision(final Prevision prevision) {
        return Observable.fromCallable(() -> {
            mAppDatabase.previsionDao().insert(prevision);
            return true;
        });
    }

    @Override
    public Observable<List<Categorie>> getCategories() {
        return mAppDatabase.categorieDao().loadAll().toObservable();
    }

    @Override
    public Observable<List<Revenu>> getRevenus() {
        return mAppDatabase.revenuDao().loadAll().toObservable();
    }

    @Override
    public Observable<List<DepenseByCategorie>> getDepenses() {
        return mAppDatabase.depenseDao().loadAll().toObservable();
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mAppDatabase.optionDao().loadAll()
                .flatMapObservable(options -> Observable.just(options.isEmpty()));
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mAppDatabase.questionDao().loadAll()
                .flatMapObservable(questions -> Observable.just(questions.isEmpty()));

    }

    @Override
    public Observable<Boolean> saveOption(final Option option) {
        return Observable.fromCallable(() -> {
            mAppDatabase.optionDao().insert(option);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveOptionList(final List<Option> optionList) {
        return Observable.fromCallable(() -> {
            mAppDatabase.optionDao().insertAll(optionList);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveQuestion(final Question question) {
        return Observable.fromCallable(() -> {
            mAppDatabase.questionDao().insert(question);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveQuestionList(final List<Question> questionList) {
        return Observable.fromCallable(() -> {
            mAppDatabase.questionDao().insertAll(questionList);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveCategorie(final Categorie categorie) {
        return Observable.fromCallable(() -> {
            mAppDatabase.categorieDao().insert(categorie);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveRevenu(final Revenu revenu) {
        return Observable.fromCallable(() -> {
            mAppDatabase.revenuDao().insert(revenu);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveDepense(final Depense depense) {
        return Observable.fromCallable(() -> {
            mAppDatabase.depenseDao().insert(depense);
            return true;
        });
    }
}
