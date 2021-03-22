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
