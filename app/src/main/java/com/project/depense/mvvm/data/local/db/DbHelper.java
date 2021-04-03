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

import io.reactivex.Observable;

/**
 * Created by lamkadmi on 17/11/19.
 */

public interface DbHelper {

    Observable<List<Question>> getAllQuestions();

    Observable<List<User>> getAllUsers();

    Observable<List<Option>> getOptionsForQuestionId(Long questionId);

    Observable<Boolean> insertUser(final User user);

    Observable<Boolean> isOptionEmpty();

    Observable<Boolean> isQuestionEmpty();

    Observable<Boolean> saveOption(Option option);

    Observable<Boolean> saveOptionList(List<Option> optionList);

    Observable<Boolean> saveQuestion(Question question);

    Observable<Boolean> saveQuestionList(List<Question> questionList);

    Observable<Boolean> saveCategorie(final Categorie categorie);

    Boolean insertCategorie(final Categorie categorie);

    Observable<Boolean> saveCategories (final List<Categorie> categories);

    Observable<Boolean> savePrevision(final Prevision prevision);

    Observable<Boolean> saveRevenu(Revenu revenu);

    Observable<Boolean> saveDepense(Depense depense);

    Observable<List<DepenseByCategorie>> getDepenseByCategories();

    Observable<List<Categorie>> getCategories();

    Observable<List<Revenu>> getRevenus();

    Observable<List<DepenseByCategorie>> getDepenses();

    Observable<List<PrevisionByCategorie>> getPrevisionsByDate(String date);
}
