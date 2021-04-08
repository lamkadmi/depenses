package com.project.depense.mvvm.ui.categorie;

import com.project.depense.mvvm.data.DataManager;
import com.project.depense.mvvm.ui.base.BaseViewModel;
import com.project.depense.mvvm.utils.rx.SchedulerProvider;

public class CategorieViewModel extends BaseViewModel<CategorieNavigator> {

    public CategorieViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
