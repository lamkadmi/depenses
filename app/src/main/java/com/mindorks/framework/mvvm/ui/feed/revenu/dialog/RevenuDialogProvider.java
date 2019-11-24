package com.mindorks.framework.mvvm.ui.feed.revenu.dialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface RevenuDialogProvider {

    @ContributesAndroidInjector
    abstract RevenuDialog provideRevenuDialogFactory();
}
