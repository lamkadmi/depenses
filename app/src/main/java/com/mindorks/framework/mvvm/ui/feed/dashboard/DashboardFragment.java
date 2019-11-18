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

package com.mindorks.framework.mvvm.ui.feed.dashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.data.model.api.BlogResponse;
import com.mindorks.framework.mvvm.databinding.FragmentDashboardBinding;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding, DashboardViewModel>
        implements DashboardNavigator, BlogAdapter.BlogAdapterListener, OnChartValueSelectedListener {

    @Inject
    BlogAdapter mBlogAdapter;

    FragmentDashboardBinding mFragmentBlogBinding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProviderFactory factory;

    private DashboardViewModel mDashboardViewModel;

    private PieChart mContainerPieView;

    public static DashboardFragment newInstance() {
        Bundle args = new Bundle();
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public DashboardViewModel getViewModel() {
        mDashboardViewModel = ViewModelProviders.of(this, factory).get(DashboardViewModel.class);
        return mDashboardViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDashboardViewModel.setNavigator(this);
        mBlogAdapter.setListener(this);
    }

    @Override
    public void onRetryClick() {
        mDashboardViewModel.fetchBlogs();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentBlogBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public void updateBlog(List<BlogResponse.Blog> blogList) {
        mBlogAdapter.addItems(blogList);
        Collection<PieEntry> pieEntries = getItemPieEntries(blogList);
        mContainerPieView.setData(getPieData(pieEntries));
        mContainerPieView.setOnChartValueSelectedListener(this);
        mContainerPieView.setTransparentCircleRadius(58f);
        mContainerPieView.setHoleRadius(58f);
        mContainerPieView.setUsePercentValues(true);
    }


    @SuppressLint("WrongConstant")
    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentBlogBinding.blogRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentBlogBinding.blogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentBlogBinding.blogRecyclerView.setAdapter(mBlogAdapter);

        mContainerPieView = mFragmentBlogBinding.chart;

        setupPieContainerView();
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mDashboardViewModel.getBlogListLiveData().observe(this, questionCardDatas -> {
            //mDashboardViewModel.setQuestionDataList(questionCardDatas);
            Collection<PieEntry> pieEntries = getItemPieEntries(questionCardDatas);
            mContainerPieView.setData(getPieData(pieEntries));
            mContainerPieView.setOnChartValueSelectedListener(this);
            mContainerPieView.setTransparentCircleRadius(58f);
            mContainerPieView.setHoleRadius(58f);
            mContainerPieView.setUsePercentValues(true);
        });
    }


    private void setupPieContainerView() {
        mContainerPieView.setUsePercentValues(true);
        mContainerPieView.getDescription().setEnabled(false);
        mContainerPieView.setExtraOffsets(5, 10, 5, 5);
        mContainerPieView.setDragDecelerationFrictionCoef(0.95f);
        // mContainerPieView.setCenterText(generateCenterSpannableText());
        mContainerPieView.setDrawHoleEnabled(true);
        mContainerPieView.setHoleColor(Color.WHITE);
        mContainerPieView.setTransparentCircleColor(Color.WHITE);
        mContainerPieView.setTransparentCircleAlpha(110);
        mContainerPieView.setHoleRadius(58f);
        mContainerPieView.setTransparentCircleRadius(61f);
        mContainerPieView.setDrawCenterText(true);
        mContainerPieView.setRotationAngle(0);
        mContainerPieView.setRotationEnabled(true);
        mContainerPieView.setHighlightPerTapEnabled(true);
        mContainerPieView.animateY(1400, Easing.EaseInSine);
        mContainerPieView.setEntryLabelColor(Color.WHITE);
        mContainerPieView.setEntryLabelTextSize(14f);
    }

    /**
     * Permet de créer les element du camembert
     * @param questionCardDatas
     * @return
     */
    @NonNull
    private Collection<PieEntry> getItemPieEntries(@NonNull List<BlogResponse.Blog> questionCardDatas) {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (BlogResponse.Blog item : questionCardDatas) {
            PieEntry pieEntry = new PieEntry(5, item.getTitle(), item);
            pieEntries.add(pieEntry);
        }
        return pieEntries;
    }

    /**
     * Permet d'initialiser le diagramme
     * @param pieEntries
     * @return
     */
    private PieData getPieData(@NonNull Collection<PieEntry> pieEntries) {
        PieDataSet dataSet = new PieDataSet(new ArrayList<>(pieEntries), "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(20f);
        dataSet.setHighlightEnabled(true);
        dataSet.setValueLinePart1OffsetPercentage(90.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        return data;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
