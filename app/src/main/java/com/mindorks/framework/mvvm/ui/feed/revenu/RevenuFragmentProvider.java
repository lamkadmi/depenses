package com.mindorks.framework.mvvm.ui.feed.revenu;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RevenuFragmentProvider {

    @ContributesAndroidInjector(modules = RevenuFragmentModule.class)
    abstract RevenuFragment provideRevenuFragmentFactory();
}
