/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.project.depense.mvvm.di.builder;

import com.project.depense.mvvm.ui.about.AboutFragmentProvider;
import com.project.depense.mvvm.ui.categorie.CategorieActivity;
import com.project.depense.mvvm.ui.categorie.CategorieActivityModule;
import com.project.depense.mvvm.ui.categorie.dialog.CategorieDialogProvider;
import com.project.depense.mvvm.ui.home.SpendingActivity;
import com.project.depense.mvvm.ui.home.SpendingActivityModule;
import com.project.depense.mvvm.ui.home.dashboard.DashboardFragmentProvider;
import com.project.depense.mvvm.ui.home.depense.DepenseFragmentProvider;
import com.project.depense.mvvm.ui.home.depense.dialog.DepenseDialogProvider;
import com.project.depense.mvvm.ui.home.revenu.RevenuFragmentProvider;
import com.project.depense.mvvm.ui.home.revenu.dialog.RevenuDialogProvider;
import com.project.depense.mvvm.ui.login.LoginActivity;
import com.project.depense.mvvm.ui.main.MainActivity;
import com.project.depense.mvvm.ui.main.MainActivityModule;
import com.project.depense.mvvm.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by lamkadmi on 17/11/19.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            SpendingActivityModule.class,
            DashboardFragmentProvider.class,
            DepenseDialogProvider.class,
            RevenuDialogProvider.class,
            DepenseFragmentProvider.class,
            CategorieDialogProvider.class,
            RevenuFragmentProvider.class})
    abstract SpendingActivity bindSpendingActivity();

    @ContributesAndroidInjector(modules = {
            CategorieActivityModule.class,
            CategorieDialogProvider.class})
    abstract CategorieActivity bindCategorieActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            AboutFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();
}
