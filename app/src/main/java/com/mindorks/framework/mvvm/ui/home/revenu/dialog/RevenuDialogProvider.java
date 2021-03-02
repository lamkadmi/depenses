package com.mindorks.framework.mvvm.ui.home.revenu.dialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface RevenuDialogProvider {

    @ContributesAndroidInjector
    abstract RevenuDialog provideRevenuDialogFactory();
}
