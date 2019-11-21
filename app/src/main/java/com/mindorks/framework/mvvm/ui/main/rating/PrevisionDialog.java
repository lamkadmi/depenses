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
import com.mindorks.framework.mvvm.ui.feed.dashboard.DashboardViewModel;

import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class PrevisionDialog extends BaseDialog implements PrevisionCallback {

    private static final String TAG = PrevisionDialog.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    private PrevisionViewModel mCategorieViewModel;

    private DashboardViewModel mDashboardViewModel;

    private DialogPrevisionBinding binding;

    public static PrevisionDialog newInstance() {
        PrevisionDialog fragment = new PrevisionDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void dismissDialog() {
        dismissDialog(TAG);

       // dashboardViewModel.fetchPrevisions();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_prevision, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);
        mDashboardViewModel = ViewModelProviders.of(this,factory).get(DashboardViewModel.class);
        binding.setViewModel(mDashboardViewModel);

        //mDashboardViewModel.setNavigator(this);
        subscribeToLiveData();
        setUp();
        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    private void subscribeToLiveData() {
        mDashboardViewModel.getCategorieListLiveData().observe(this, mDatas -> {
            ArrayAdapter<Categorie> dataAdapter = new ArrayAdapter<>(this.getContext(),R.layout.support_simple_spinner_dropdown_item,mDatas);
            binding.categorie.setAdapter(dataAdapter);

        });
    }

    private void setUp(){
        binding.champDateValeur.setOnClickListener(view -> new RackMonthPicker(getContext())
                .setLocale(Locale.FRANCE)
                .setPositiveButton((month, startDate, endDate, year, monthLabel) -> {
                    String date = month+"/"+year;
                    binding.champDateValeur.setText(date);
                })
                .setNegativeButton(dialog -> {
                    dialog.dismiss();
                }).show());
    }


}
