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

package com.mindorks.framework.mvvm.ui.categorie.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.databinding.DialogCategorieBinding;
import com.mindorks.framework.mvvm.ui.base.BaseDialog;
import com.mindorks.framework.mvvm.ui.home.dashboard.dialog.PrevisionDialogNavigator;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class CategorieDialog extends BaseDialog implements PrevisionDialogNavigator {

    private static final String TAG = CategorieDialog.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    private CategorieDialogViewModel mCategorieViewModel;

    private DialogCategorieBinding mDialogCategorieBinding;

    private CategorieDialogCallback listener;

    public static CategorieDialog newInstance() {
        CategorieDialog fragment = new CategorieDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


//    @Override
//    public void dismissDialog() {
//        dismissDialog(TAG);
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDialogCategorieBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_categorie, container, false);
        View view = mDialogCategorieBinding.getRoot();

        AndroidSupportInjection.inject(this);

        mCategorieViewModel = ViewModelProviders.of(this, factory).get(CategorieDialogViewModel.class);

        mDialogCategorieBinding.setViewModel(mCategorieViewModel);
        mCategorieViewModel.setNavigator(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //dashboardViewModel = ViewModelProviders.of(getActivity(),factory).get(DashboardViewModel.class);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void setListener(CategorieDialogCallback listener) {
        this.listener = listener;
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
        listener.updateDashboard();
    }
}
