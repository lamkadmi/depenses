package com.mindorks.framework.mvvm.ui.feed.revenu.dialog;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.db.Revenu;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableField;

public class RevenuDialogViewModel extends BaseViewModel<RevenuDialogNavigator> {

    private final ObservableField<String> date = new ObservableField<>();

    private final ObservableField<String> description = new ObservableField<>();

    private final ObservableField<String> revenu = new ObservableField<>();

    public RevenuDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        date.set("1/2019");
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public ObservableField<String> getRevenu() {
        return revenu;
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .saveRevenu(new Revenu(getDate().get(),Float.valueOf(getRevenu().get()),getDescription().get()))
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
