package com.project.depense.mvvm.ui.home.revenu.dialog;

import com.project.depense.mvvm.data.DataManager;
import com.project.depense.mvvm.data.model.db.Revenu;
import com.project.depense.mvvm.ui.base.BaseViewModel;
import com.project.depense.mvvm.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableField;

/**
 * Created by lamkadmi on 17/11/19.
 */
public class RevenuDialogViewModel extends BaseViewModel<RevenuDialogNavigator> {

    private final ObservableField<String> description = new ObservableField<>();

    private final ObservableField<String> revenu = new ObservableField<>();

    private final ObservableField<String> revenuDate = new ObservableField<>();

    public RevenuDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .saveRevenu(new Revenu(getRevenuDate().get(), Float.valueOf(getRevenu().get()), getDescription().get()))
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

    public ObservableField<String> getDescription() {
        return description;
    }

    public ObservableField<String> getRevenu() {
        return revenu;
    }

    public ObservableField<String> getRevenuDate() {
        return revenuDate;
    }
}
