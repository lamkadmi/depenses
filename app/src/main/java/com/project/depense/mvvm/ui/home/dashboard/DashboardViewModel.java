package com.project.depense.mvvm.ui.home.dashboard;

import com.project.depense.mvvm.data.DataManager;
import com.project.depense.mvvm.data.model.others.DepenseByCategorie;
import com.project.depense.mvvm.ui.base.BaseViewModel;
import com.project.depense.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class DashboardViewModel extends BaseViewModel<DashboardNavigator> {

    private final MutableLiveData<List<DepenseByCategorie>> depenseByCategoriesListLiveData;

    private final MutableLiveData<List<DepenseByCategorie>> depenseItemsLiveData;


    public DashboardViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        depenseByCategoriesListLiveData = new MutableLiveData<>();
        depenseItemsLiveData = new MutableLiveData<>();
        //fetchPrevisions();
        //fetchDepenses();
        //fetchDepensesByCategorie();
    }

    public void fetchDepensesByCategorie() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getDepenseByCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(depenseByCategories -> {
                    if (depenseByCategories != null) {
                        depenseByCategoriesListLiveData.setValue(depenseByCategories);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void fetchAllDepenses() {
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

    public MutableLiveData<List<DepenseByCategorie>> getDepensesListLiveData() {
        return depenseItemsLiveData;
    }

    public MutableLiveData<List<DepenseByCategorie>> getDepenseByCategorieBarChartLiveData() {
        return depenseByCategoriesListLiveData;
    }
}
