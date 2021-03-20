package com.project.depense.mvvm.ui.home.depense.dialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface DepenseDialogProvider {

    @ContributesAndroidInjector
    abstract DepenseDialog provideDepenseDialogFactory();
}
