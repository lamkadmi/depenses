package com.mindorks.framework.mvvm.ui.home.revenu;

import android.os.Bundle;
import android.view.View;

import com.kal.rackmonthpicker.RackMonthPicker;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.databinding.ActivitySpendingBinding;
import com.mindorks.framework.mvvm.databinding.FragmentRevenuBinding;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;
import com.mindorks.framework.mvvm.ui.home.SpendingActivity;
import com.mindorks.framework.mvvm.ui.home.revenu.dialog.RevenuDialog;
import com.mindorks.framework.mvvm.ui.home.revenu.dialog.RevenuDialogCallback;

import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RevenuFragment extends BaseFragment<FragmentRevenuBinding, RevenuViewModel> implements
        RevenuNavigator, RevenuDialogCallback {

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProviderFactory mFactory;

    @Inject
    RevenuAdapter mRevenuRecyclerViewAdapter;

    private RevenuViewModel mRevenuViewModel;

    FragmentRevenuBinding mFragmentRevenuBinding;

    private ActivitySpendingBinding mActivitySpendingBinding;


    public static RevenuFragment newInstance() {
        Bundle args = new Bundle();
        RevenuFragment fragment = new RevenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_revenu;
    }

    @Override
    public RevenuViewModel getViewModel() {
        mRevenuViewModel = ViewModelProviders.of(this, mFactory).get(RevenuViewModel.class);
        return mRevenuViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRevenuViewModel.setNavigator(this);
        mActivitySpendingBinding = (ActivitySpendingBinding) getBaseActivity().getViewDataBinding();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentRevenuBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFragmentRevenuBinding.revenuRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentRevenuBinding.revenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentRevenuBinding.revenuRecyclerView.setAdapter(mRevenuRecyclerViewAdapter);
        mFragmentRevenuBinding.champDateValeur.setOnClickListener(view -> new RackMonthPicker(getContext())
                .setLocale(Locale.FRANCE)
                .setPositiveButton((month, startDate, endDate, year, monthLabel) -> {
                    String date = month + "/" + year;
                    mFragmentRevenuBinding.champDateValeur.setText(date);
                    mRevenuViewModel.fetchRevenus();
                })
                .setNegativeButton(dialog -> {
                    dialog.dismiss();
                }).show());

        subscribeToLiveData();

        ((SpendingActivity) getActivity()).setRevenuFragmentListener(() -> setupRevenuDialog());
    }

    private void setupRevenuDialog() {
        RevenuDialog dialog = RevenuDialog.newInstance();
        dialog.setListener(this);
        dialog.show(getFragmentManager());
    }

    private void subscribeToLiveData() {
        mRevenuViewModel.getRevenuListLiveData().observe(this, mRevenus -> {
            mRevenuRecyclerViewAdapter.addItems(mRevenus);
        });
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void updateListeRevenu() {
        mRevenuViewModel.fetchRevenus();
    }
}
