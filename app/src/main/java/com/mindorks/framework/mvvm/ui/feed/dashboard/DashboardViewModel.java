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

package com.mindorks.framework.mvvm.ui.feed.dashboard;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.others.PrevisionByCategorie;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class DashboardViewModel extends BaseViewModel<DashboardNavigator> {

    private final MutableLiveData<List<PrevisionByCategorie>> previsionListLiveData;



    public DashboardViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        previsionListLiveData = new MutableLiveData<>();
        fetchPrevisions();
    }

    public void fetchPrevisions() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getPrevisions()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(previsions -> {
                    if (previsions != null) {
                        previsionListLiveData.setValue(previsions);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void fetchPrevisionsByDate(String date) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getPrevisionsByDate(date)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(previsions -> {
                    if (previsions != null) {
                        previsionListLiveData.setValue(previsions);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public LiveData<List<PrevisionByCategorie>> getDashboardListLiveData() {
        return previsionListLiveData;
    }
}
