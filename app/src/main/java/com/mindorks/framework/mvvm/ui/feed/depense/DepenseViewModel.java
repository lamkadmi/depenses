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

package com.mindorks.framework.mvvm.ui.feed.depense;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.OpenSourceResponse;
import com.mindorks.framework.mvvm.data.model.db.Depense;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class DepenseViewModel extends BaseViewModel<DepenseNavigator> {

    private final MutableLiveData<List<Depense>> depenseItemsLiveData;

    public DepenseViewModel(DataManager dataManager,
                            SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        depenseItemsLiveData = new MutableLiveData<>();
        fetchDepenses();
    }

//    public void fetchRepos() {
//        setIsLoading(true);
//        getCompositeDisposable().add(getDataManager()
//                .getOpenSourceApiCall()
//                .map(openSourceResponse -> openSourceResponse.getData())
//                .flatMap(this::getViewModelList)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(openSourceResponse -> {
//                    depenseItemsLiveData.setValue(openSourceResponse);
//                    setIsLoading(false);
//                }, throwable -> {
//                    setIsLoading(false);
//                    getNavigator().handleError(throwable);
//                }));
//    }

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

    public LiveData<List<Depense>> getDepenseItemsLiveData() {
        return depenseItemsLiveData;
    }

    private Single<List<DepenseItemViewModel>> getViewModelList(List<OpenSourceResponse.Repo> repoList) {
        return Observable.fromIterable(repoList)
                .map(repo -> new DepenseItemViewModel(
                        repo.getCoverImgUrl(), repo.getTitle(),
                        repo.getDescription(), repo.getProjectUrl())).toList();
    }
}
