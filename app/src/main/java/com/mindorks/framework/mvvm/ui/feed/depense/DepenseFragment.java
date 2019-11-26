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

package com.mindorks.framework.mvvm.ui.feed.depense;


import android.os.Bundle;
import android.view.View;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.databinding.FragmentDepenseBinding;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;
import com.mindorks.framework.mvvm.ui.feed.depense.dialog.DepenseDialog;
import com.mindorks.framework.mvvm.ui.feed.depense.dialog.DepenseDialogCallback;
import com.mindorks.framework.mvvm.ui.main.rating.PrevisionDialog;
import com.mindorks.framework.mvvm.ui.main.rating.PrevisionDialogCallback;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class DepenseFragment extends BaseFragment<FragmentDepenseBinding, DepenseViewModel>
        implements DepenseNavigator, DepenseAdapter.DepenseAdapterListener,DepenseDialogCallback {

    FragmentDepenseBinding mFragmentDepenseBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    DepenseAdapter mDepenseAdapter;
    @Inject
    ViewModelProviderFactory factory;
    private DepenseViewModel mDepenseViewModel;

    public static DepenseFragment newInstance() {
        Bundle args = new Bundle();
        DepenseFragment fragment = new DepenseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_depense;
    }

    @Override
    public DepenseViewModel getViewModel() {
        mDepenseViewModel = ViewModelProviders.of(this, factory).get(DepenseViewModel.class);
        return mDepenseViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDepenseViewModel.setNavigator(this);
        mDepenseAdapter.setListener(this);
    }

    @Override
    public void onRetryClick() {
        mDepenseViewModel.fetchDepenses();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentDepenseBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFragmentDepenseBinding.openSourceRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentDepenseBinding.openSourceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentDepenseBinding.openSourceRecyclerView.setAdapter(mDepenseAdapter);
        mFragmentDepenseBinding.addDepense.setOnClickListener(v -> {
            DepenseDialog dialog =  DepenseDialog.newInstance();
            dialog.setListener(this);
            dialog.show(getFragmentManager());
        });

    }

    @Override
    public void updateListeDepenses() {
        mDepenseViewModel.fetchDepenses();
    }
}
