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

package com.mindorks.framework.mvvm.ui.main.rating;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kal.rackmonthpicker.RackMonthPicker;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.data.model.db.Categorie;
import com.mindorks.framework.mvvm.databinding.DialogPrevisionBinding;
import com.mindorks.framework.mvvm.ui.base.BaseDialog;

import java.util.Locale;

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

public class PrevisionDialog extends BaseDialog implements PrevisionDialogNavigator {

    private static final String TAG = PrevisionDialog.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    private PrevisionDialogViewModel mPrevisionViewModel;

    //private DashboardViewModel dashboardViewModel;

    private DialogPrevisionBinding mDialogPrevisionBinding;

    private PrevisionDialogCallback listener;

    public static PrevisionDialog newInstance() {
        PrevisionDialog fragment = new PrevisionDialog();
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
        mDialogPrevisionBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_prevision, container, false);
        View view = mDialogPrevisionBinding.getRoot();

        AndroidSupportInjection.inject(this);

        mPrevisionViewModel = ViewModelProviders.of(this,factory).get(PrevisionDialogViewModel.class);

        mDialogPrevisionBinding.setViewModel(mPrevisionViewModel);
        mPrevisionViewModel.setNavigator(this);
        setUp();
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

    private void subscribeToLiveData() {
        mPrevisionViewModel.getCategorieListLiveData().observe(this, mDatas -> {
            ArrayAdapter<Categorie> dataAdapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item,mDatas);
            mDialogPrevisionBinding.categorie.setAdapter(dataAdapter);
        });
    }

    private void setUp(){
        subscribeToLiveData();
        mDialogPrevisionBinding.champDateValeur.setOnClickListener(view -> new RackMonthPicker(getContext())
                .setLocale(Locale.ENGLISH)
                .setPositiveButton((month, startDate, endDate, year, monthLabel) -> {
                    String date = month+"/"+year;
                    mDialogPrevisionBinding.champDateValeur.setText(date);
                })
                .setNegativeButton(dialog -> {
                    dialog.dismiss();
                }).show());
    }


    public void setListener(PrevisionDialogCallback listener) {
        this.listener = listener;
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
        listener.updateDashboard();
    }
}
