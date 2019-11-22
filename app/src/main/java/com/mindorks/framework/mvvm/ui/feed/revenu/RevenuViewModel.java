package com.mindorks.framework.mvvm.ui.feed.revenu;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.db.Revenu;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

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
                .subscribe(previsions -> {
                    if (previsions != null) {
                        revenuListLiveData.setValue(previsions);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
