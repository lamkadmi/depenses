package com.mindorks.framework.mvvm.ui.home.dashboard;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.db.Categorie;
import com.mindorks.framework.mvvm.data.model.others.DepenseByCategorie;
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

    private final MutableLiveData<List<Categorie>> categorieListLiveData;

    private final MutableLiveData<List<DepenseByCategorie>> depenseItemsLiveData;


    public DashboardViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        previsionListLiveData = new MutableLiveData<>();
        categorieListLiveData = new MutableLiveData<>();
        depenseItemsLiveData = new MutableLiveData<>();
        fetchPrevisions();
        fetchCategories();
        fetchDepenses();
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

    public void fetchCategories() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(previsions -> {
                    if (previsions != null) {
                        categorieListLiveData.setValue(previsions);
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

    public LiveData<List<PrevisionByCategorie>> getDashboardListLiveData() {
        return previsionListLiveData;
    }

    public MutableLiveData<List<Categorie>> getCategorieListLiveData() {
        return categorieListLiveData;
    }

    public MutableLiveData<List<DepenseByCategorie>> getDepenseItemsLiveData() {
        return depenseItemsLiveData;
    }
}
