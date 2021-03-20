package com.mindorks.framework.mvvm.ui.home.revenu;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.db.Revenu;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RevenuViewModel extends BaseViewModel<RevenuNavigator> {

    private final MutableLiveData<List<Revenu>> revenuListLiveData;

    public RevenuViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        this.revenuListLiveData = new MutableLiveData<>();
        fetchRevenus();
    }

    public LiveData<List<Revenu>> getRevenuListLiveData() {
        return revenuListLiveData;
    }

    public void fetchRevenus() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getRevenus()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(revenus -> {
                    if (revenus != null) {
                        revenus = revenus.stream()
                                .filter(revenu -> revenu.getMois_annee()!=null)
                                .sorted(Comparator.comparing(Revenu::getMois_annee))
                                .collect(Collectors.toList());
                        revenuListLiveData.setValue(revenus);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
