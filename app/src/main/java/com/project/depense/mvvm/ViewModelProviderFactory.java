package com.project.depense.mvvm;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.project.depense.mvvm.data.DataManager;
import com.project.depense.mvvm.ui.about.AboutViewModel;
import com.project.depense.mvvm.ui.categorie.CategorieViewModel;
import com.project.depense.mvvm.ui.home.SpendingViewModel;
import com.project.depense.mvvm.ui.categorie.dialog.CategorieDialogViewModel;
import com.project.depense.mvvm.ui.home.dashboard.DashboardViewModel;
import com.project.depense.mvvm.ui.home.depense.DepenseViewModel;
import com.project.depense.mvvm.ui.home.depense.dialog.DepenseDialogViewModel;
import com.project.depense.mvvm.ui.home.revenu.RevenuViewModel;
import com.project.depense.mvvm.ui.home.revenu.dialog.RevenuDialogViewModel;
import com.project.depense.mvvm.ui.login.LoginViewModel;
import com.project.depense.mvvm.ui.main.MainViewModel;
import com.project.depense.mvvm.ui.splash.SplashViewModel;
import com.project.depense.mvvm.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by fouzi on 22/02/19.
 */
@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AboutViewModel.class)) {
            //noinspection unchecked
            return (T) new AboutViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(SpendingViewModel.class)) {
            //noinspection unchecked
            return (T) new SpendingViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //noinspection unchecked
            return (T) new MainViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(DashboardViewModel.class)) {
            //noinspection unchecked
            return (T) new DashboardViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(RevenuDialogViewModel.class)) {
            //noinspection unchecked
            return (T) new RevenuDialogViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(DepenseDialogViewModel.class)) {
            //noinspection unchecked
            return (T) new DepenseDialogViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(DepenseViewModel.class)) {
            //noinspection unchecked
            return (T) new DepenseViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(RevenuViewModel.class)) {
            //noinspection unchecked
            return (T) new RevenuViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(CategorieDialogViewModel.class)) {
            //noinspection unchecked
            return (T) new CategorieDialogViewModel(dataManager, schedulerProvider);
        } else if (modelClass.isAssignableFrom(CategorieViewModel.class)) {
            //noinspection unchecked
            return (T) new CategorieViewModel(dataManager, schedulerProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}