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

package com.project.depense.mvvm.ui.home.depense;

import com.project.depense.mvvm.data.DataManager;
import com.project.depense.mvvm.data.model.others.DepenseByCategorie;
import com.project.depense.mvvm.ui.base.BaseViewModel;
import com.project.depense.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class DepenseViewModel extends BaseViewModel<DepenseNavigator> {

    private final MutableLiveData<List<DepenseByCategorie>> depenseItemsLiveData;

    public DepenseViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        depenseItemsLiveData = new MutableLiveData<>();
        fetchDepenses();
    }

    public void fetchDepenses() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getDepenses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(depenses -> {
                    if (depenses != null) {
                        depenseItemsLiveData.setValue(depenses);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public LiveData<List<DepenseByCategorie>> getDepenseItemsLiveData() {
        return depenseItemsLiveData;
    }
}
