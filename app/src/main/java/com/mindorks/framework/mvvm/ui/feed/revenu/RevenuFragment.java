package com.mindorks.framework.mvvm.ui.feed.revenu;

import android.os.Bundle;
import android.view.View;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.databinding.FragmentRevenuBinding;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RevenuFragment extends BaseFragment<FragmentRevenuBinding,RevenuViewModel> implements RevenuNavigator {

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProviderFactory mFactory;

    @Inject
    RevenuAdapter mRevenuRecyclerViewAdapter;

    private RevenuViewModel mRevenuViewModel;

    FragmentRevenuBinding mFragmentRevenuBinding;

    public static RevenuFragment newInstance(){
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
        mRevenuViewModel = ViewModelProviders.of(this,mFactory).get(RevenuViewModel.class);
        return mRevenuViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRevenuViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentRevenuBinding = getViewDataBinding();
        setUp();

    }

    private void setUp(){
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFragmentRevenuBinding.revenuRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentRevenuBinding.revenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentRevenuBinding.revenuRecyclerView.setAdapter(mRevenuRecyclerViewAdapter);
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mRevenuViewModel.getRevenuListLiveData().observe(this, mRevenus -> {
            mRevenuRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
