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

package com.mindorks.framework.mvvm.ui.main.rating;

import android.view.View;
import android.widget.AdapterView;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.db.Categorie;
import com.mindorks.framework.mvvm.data.model.db.Prevision;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class PrevisionDialogViewModel extends BaseViewModel<PrevisionDialogNavigator> {

    private final MutableLiveData<List<Categorie>> categorieListLiveData;

    private final ObservableField<String> date = new ObservableField<>();

    private final ObservableField<Categorie> categorie = new ObservableField<>();

    private final ObservableField<String> montant = new ObservableField<>();

    public PrevisionDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        categorieListLiveData = new MutableLiveData<>();
        date.set("1/2019");
        fetchCategories();
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public ObservableField<Categorie> getCategorie() {
        return categorie;
    }

    public ObservableField<String> getMontant() {
        return montant;
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .savePrevision(new Prevision(getCategorie().get().getId(),
                        getDate().get(),
                        Float.valueOf(getMontant().get())
                ))
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

    public void fetchCategories() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(blogResponse -> {
                    if (blogResponse != null) {
                        categorieListLiveData.setValue(blogResponse);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    //getNavigator().handleError(throwable);
                }));
    }

    public LiveData<List<Categorie>> getCategorieListLiveData() {
        return categorieListLiveData;
    }

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id){
        categorie.set((Categorie) parent.getSelectedItem());
        //pos                                 get selected item position
        //view.getText()                      get lable of selected item
        //parent.getAdapter().getItem(pos)    get item by pos
        //parent.getAdapter().getCount()      get item count
        //parent.getCount()                   get item count
        //parent.getSelectedItem()            get selected item
        //and other...
    }

}
