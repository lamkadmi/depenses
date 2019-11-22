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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.data.model.others.PrevisionByCategorie;
import com.mindorks.framework.mvvm.databinding.FragmentDashboardBinding;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;
import com.mindorks.framework.mvvm.ui.main.rating.PrevisionDialog;
import com.mindorks.framework.mvvm.ui.main.rating.PrevisionDialogCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding, DashboardViewModel>
        implements DashboardNavigator, PrevisionAdapter.PrevisionAdapterListener, OnChartValueSelectedListener, PrevisionDialogCallback {

    @Inject
    PrevisionAdapter mPrevisionRecyclerViewAdapter;

    FragmentDashboardBinding mFragmentDashboardBinding;

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
        mPrevisionRecyclerViewAdapter.setListener(this);
    }

    @Override
    public void onRetryClick() {
        mDashboardViewModel.fetchPrevisions();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentDashboardBinding = getViewDataBinding();
        subscribeToLiveData();
        setUp();
    }

    @Override
    public void updatePrevisions(List<PrevisionByCategorie> blogList) {
        mPrevisionRecyclerViewAdapter.addItems(blogList);
        Collection<PieEntry> pieEntries = getPrevisionPieEntries(blogList);
        mContainerPieView.setData(getPieData(pieEntries));
        mContainerPieView.setOnChartValueSelectedListener(this);
        mContainerPieView.setTransparentCircleRadius(58f);
        mContainerPieView.setHoleRadius(58f);
        mContainerPieView.setUsePercentValues(true);
    }

    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFragmentDashboardBinding.blogRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentDashboardBinding.blogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentDashboardBinding.blogRecyclerView.setAdapter(mPrevisionRecyclerViewAdapter);

        mFragmentDashboardBinding.champDateValeur.setOnClickListener(view -> new RackMonthPicker(getContext())
                .setLocale(Locale.ENGLISH)
                .setPositiveButton((month, startDate, endDate, year, monthLabel) -> {
                    String date = month+"/"+year;
                    mFragmentDashboardBinding.champDateValeur.setText(date);
                    mDashboardViewModel.fetchPrevisionsByDate(date);
                })
                .setNegativeButton(dialog -> {
                    dialog.dismiss();
                }).show());


        mContainerPieView = mFragmentDashboardBinding.chart;
        mFragmentDashboardBinding.addPrevision.setOnClickListener(v -> {
            PrevisionDialog dialog =  PrevisionDialog.newInstance();
            dialog.setListener(this);
            dialog.show(getFragmentManager());
        });

        setupPieContainerView();

    }

    private void subscribeToLiveData() {
        mDashboardViewModel.getDashboardListLiveData().observe(this, mPrevisions -> {
            setupPieContainerView();
            Collection<PieEntry> pieEntries = getPrevisionPieEntries(mPrevisions);
            mContainerPieView.setData(getPieData(pieEntries));
            mPrevisionRecyclerViewAdapter.notifyDataSetChanged();

        });
    }


    private void setupPieContainerView() {
        mContainerPieView.setUsePercentValues(true);
        mContainerPieView.getDescription().setEnabled(false);
        //mContainerPieView.setExtraOffsets(5, 10, 5, 5);
        mContainerPieView.setDragDecelerationFrictionCoef(0.95f);
        // mContainerPieView.setCenterText(generateCenterSpannableText());
        mContainerPieView.setDrawHoleEnabled(false);
        mContainerPieView.setHoleColor(Color.WHITE);
        mContainerPieView.setTransparentCircleColor(Color.WHITE);
        mContainerPieView.setTransparentCircleAlpha(110);
        mContainerPieView.setHoleRadius(58f);
        mContainerPieView.setTransparentCircleRadius(61f);
        mContainerPieView.setDrawCenterText(false);
        mContainerPieView.setRotationAngle(0);
        mContainerPieView.setRotationEnabled(false);
        mContainerPieView.setHighlightPerTapEnabled(true);
        mContainerPieView.animateY(1400, Easing.EaseInSine);
        mContainerPieView.setEntryLabelColor(Color.BLACK);
        mContainerPieView.setEntryLabelTextSize(10f);

    }

    /**
     * Permet de créer les element du camembert
     * @param questionCardDatas
     * @return
     */
    @NonNull
    private Collection<PieEntry> getPrevisionPieEntries(@NonNull List<PrevisionByCategorie> questionCardDatas) {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (PrevisionByCategorie item : questionCardDatas) {
            PieEntry pieEntry = new PieEntry(item.getMontant(), item.getCategorie(), item);
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
       // dataSet.setIconsOffset(new MPPointF(0, 40));
        //dataSet.setSelectionShift(20f);
        dataSet.setHighlightEnabled(true);
        //dataSet.setValueLinePart1OffsetPercentage(90.f);
        //dataSet.setValueLinePart1Length(0.2f);
        //dataSet.setValueLinePart2Length(0.4f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //dataSet.setValueLineColor(Color.BLACK);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        return data;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    @Override
    public void updateDashboard() {
        mDashboardViewModel.fetchPrevisions();
    }
}
