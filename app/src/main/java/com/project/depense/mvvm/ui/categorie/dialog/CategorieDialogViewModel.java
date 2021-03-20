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

package com.project.depense.mvvm.ui.categorie.dialog;

import com.project.depense.mvvm.data.DataManager;
import com.project.depense.mvvm.data.model.db.Categorie;
import com.project.depense.mvvm.ui.base.BaseViewModel;
import com.project.depense.mvvm.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableField;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class CategorieDialogViewModel extends BaseViewModel<CategorieDialogNavigator> {


    private final ObservableField<String> categorie = new ObservableField<>();

    public CategorieDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getCategorie() {
        return categorie;
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .saveCategorie(new Categorie(getCategorie().get()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        getNavigator().dismissDialog();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    //getNavigator().handleError(throwable);
                }));

    }

}
