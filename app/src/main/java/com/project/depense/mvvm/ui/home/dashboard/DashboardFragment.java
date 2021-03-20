package com.project.depense.mvvm.ui.home.dashboard;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.project.depense.mvvm.ViewModelProviderFactory;
import com.project.depense.mvvm.data.model.others.DepenseByCategorie;
import com.project.depense.mvvm.data.model.others.PrevisionByCategorie;
import com.mindorks.framework.mvvm.databinding.FragmentDashboardBinding;
import com.project.depense.mvvm.ui.base.BaseFragment;
import com.project.depense.mvvm.ui.home.SpendingActivity;
import com.project.depense.mvvm.ui.home.depense.DepenseAdapter;
import com.project.depense.mvvm.ui.home.depense.dialog.DepenseDialog;
import com.project.depense.mvvm.ui.home.depense.dialog.DepenseDialogCallback;
import com.project.depense.mvvm.utils.AppUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
        implements DashboardNavigator, OnChartValueSelectedListener, DepenseAdapter.DepenseAdapterListener,
        DepenseDialogCallback {

    @Inject
    PrevisionAdapter mPrevisionRecyclerViewAdapter;

    @Inject
    DepenseAdapter mDepenseRecyclerViewAdapter;

    FragmentDashboardBinding mFragmentDashboardBinding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProviderFactory factory;

    private DashboardViewModel mDashboardViewModel;

    private PieChart mContainerPieView;

    private BarChart mContainerBarView;

    ArrayList<String> severityStringList;

    public static DashboardFragment newInstance() {
        Bundle args = new Bundle();
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        mDepenseRecyclerViewAdapter.setListener(this);
        mDashboardViewModel.fetchAllDepenses();
        mDashboardViewModel.fetchDepensesByCategorie();
    }

    @Override
    public void onRetryClick() {
        mDashboardViewModel.fetchAllDepenses();
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
    }

    @Override
    public void updateListeDepenses() {
        mDashboardViewModel.fetchAllDepenses();
        mDashboardViewModel.fetchDepensesByCategorie();
    }

    private void setUp() {
        ((SpendingActivity) getActivity()).setDashboardFragmentListener(() -> setupDepenseDialog());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mFragmentDashboardBinding.depensesRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentDashboardBinding.depensesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentDashboardBinding.depensesRecyclerView.setAdapter(mDepenseRecyclerViewAdapter);

        mFragmentDashboardBinding.champDateValeur.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener listener = (v1, year, monthOfYear, dayOfMonth) -> {
                String data = AppUtils.getDate(year, monthOfYear, dayOfMonth);
                mFragmentDashboardBinding.champDateValeur.setText(data);
            };
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            DatePickerDialog dpDialog = new DatePickerDialog(this.getContext(), listener, cal.get(1), cal.get(2), cal.get(5));
            dpDialog.show();
        });

        mContainerPieView = mFragmentDashboardBinding.chart;

        mContainerBarView = mFragmentDashboardBinding.chart1;
    }

    /**
     * Display Depense dialog
     */
    private void setupDepenseDialog() {
        DepenseDialog dialog = DepenseDialog.newInstance();
        dialog.setListener(this);
        dialog.show(getFragmentManager());
    }

    private void subscribeToLiveData() {
        mDashboardViewModel.getDepensesListLiveData().observe(this, mAllDepenses -> {
            mDepenseRecyclerViewAdapter.notifyDataSetChanged();
        });
        mDashboardViewModel.getDepenseByCategorieBarChartLiveData().observe(this, mDepensesByCategories -> {
            setupDepenseByCategorieBarChartView(mDepensesByCategories);
        });
    }

    private void setupDepenseByCategorieBarChartView(List<DepenseByCategorie> pData) {
        BarData data = createChartData(pData);
        configureChartAppearance();
        prepareChartData(data);
    }

    private BarData createChartData(List<DepenseByCategorie> depensesByCategories) {
        List<BarEntry> barEntries = new ArrayList<>();
        severityStringList = new ArrayList<>();
        for (DepenseByCategorie item : depensesByCategories) {
            severityStringList.add(item.getCategorie());
            BarEntry pieEntry = new BarEntry(depensesByCategories.indexOf(item), item.getMontant());
            barEntries.add(pieEntry);
        }
        BarDataSet dataSet = new BarDataSet(barEntries, "");

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        dataSets.add(dataSet);

        BarData data = new BarData(dataSet);

        return data;
    }

    private void configureChartAppearance() {
        mContainerBarView.getDescription().setEnabled(false);
        mContainerBarView.setDrawValueAboveBar(false);
        //mContainerBarView.setMaxVisibleValueCount(4);
        //mContainerBarView.getXAxis().setDrawGridLines(false);
        mContainerBarView.setPinchZoom(false);
        mContainerBarView.setDrawBarShadow(false);
        mContainerBarView.setDrawGridBackground(false);

        XAxis xAxis = mContainerBarView.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(severityStringList));
        xAxis.setGranularity(1f);
        mContainerBarView.getAxisLeft().setDrawGridLines(false);
        mContainerBarView.getAxisLeft().setGranularity(10f);
        //mContainerBarView.getAxisLeft().setAxisMinimum(2);
        mContainerBarView.getAxisRight().setDrawGridLines(false);
        mContainerBarView.getAxisRight().setEnabled(false);
        mContainerBarView.getAxisLeft().setEnabled(true);
        mContainerBarView.getXAxis().setDrawGridLines(false);
        mContainerBarView.animateY(1500);


        mContainerBarView.getLegend().setEnabled(false);

        mContainerBarView.getAxisRight().setDrawLabels(false);
        mContainerBarView.getAxisLeft().setDrawLabels(true);
        mContainerBarView.setTouchEnabled(false);
        mContainerBarView.setDoubleTapToZoomEnabled(false);
        mContainerBarView.getXAxis().setEnabled(true);
        mContainerBarView.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mContainerBarView.invalidate();

        /* YAxis axisLeft = mContainerBarView.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = mContainerBarView.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);*/
    }

    private void prepareChartData(BarData data) {
        data.setValueTextSize(10f);
        mContainerBarView.setData(data);
        mContainerBarView.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


}
