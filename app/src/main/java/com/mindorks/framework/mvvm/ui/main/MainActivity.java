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

package com.mindorks.framework.mvvm.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
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
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.navigation.NavigationView;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.BuildConfig;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.data.model.others.QuestionCardData;
import com.mindorks.framework.mvvm.databinding.ActivityMainBinding;
import com.mindorks.framework.mvvm.databinding.NavHeaderMainBinding;
import com.mindorks.framework.mvvm.ui.about.AboutFragment;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.feed.FeedActivity;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;
import com.mindorks.framework.mvvm.ui.main.rating.RateUsDialog;
import com.mindorks.framework.mvvm.utils.ScreenUtils;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, OnChartValueSelectedListener {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    ObjectifAdapter mObjectifAdapter;

    private ActivityMainBinding mActivityMainBinding;
    private SwipePlaceHolderView mContainerCardView;
    private PieChart mContainerPieView;
    private DrawerLayout mDrawer;
    private MainViewModel mMainViewModel;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;



    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG);
        if (fragment == null) {
            super.onBackPressed();
        } else {
            onFragmentDetached(AboutFragment.TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        switch (item.getItemId()) {
            case R.id.action_cut:
                return true;
            case R.id.action_copy:
                return true;
            case R.id.action_share:
                return true;
            case R.id.action_delete:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void lockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void setUp() {
        mDrawer = mActivityMainBinding.drawerView;
        mToolbar = mActivityMainBinding.toolbar;
        mNavigationView = mActivityMainBinding.navigationView;
        mContainerCardView = mActivityMainBinding.cardsContainer;
        mContainerPieView = mActivityMainBinding.piechart;

        mActivityMainBinding.objectifs.setAdapter(mObjectifAdapter);

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mMainViewModel.updateAppVersion(version);
        mMainViewModel.onNavMenuCreated();
        setupCardContainerView();
        setupPieContainerView();
        subscribeToLiveData();
    }

    private void setupCardContainerView() {
        int screenWidth = ScreenUtils.getScreenWidth(this);
        int screenHeight = ScreenUtils.getScreenHeight(this);

        mContainerCardView.getBuilder()
                .setDisplayViewCount(3)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth((int) (0.90 * screenWidth))
                        .setViewHeight((int) (0.75 * screenHeight))
                        .setPaddingTop(20)
                        .setSwipeRotationAngle(10)
                        .setRelativeScale(0.01f));

        mContainerCardView.addItemRemoveListener(count -> {
            if (count == 0) {
                // reload the contents again after 1 sec delay
                new Handler(getMainLooper()).postDelayed(() -> {
                    //Reload once all the cards are removed
                    mMainViewModel.loadQuestionCards();
                }, 800);
            } else {
                mMainViewModel.removeQuestionCard();
            }
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

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Mes dépenses");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 17, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 20, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 17, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), s.length() - 15, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(.8f), 17, s.length() - 16, 0);
        s.setSpan(new RelativeSizeSpan(.8f), s.length()-1, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 17, s.length()-1, 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 17, s.length()-1, 0);
        return s;
    }

    private void setupNavMenu() {
        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, mActivityMainBinding.navigationView, false);
        mActivityMainBinding.navigationView.addHeaderView(navHeaderMainBinding.getRoot());
        //navHeaderMainBinding.setViewModel(mMainViewModel);

        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.navItemAbout:
                            showAboutFragment();
                            return true;
//                        case R.id.navItemRateUs:
//                            RateUsDialog.newInstance().show(getSupportFragmentManager());
//                            return true;
                        case R.id.navItemFeed:
                            startActivity(FeedActivity.newIntent(MainActivity.this));
                            return true;
                        case R.id.navItemLogout:
                            mMainViewModel.logout();
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void showAboutFragment() {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
    }

    private void subscribeToLiveData() {
        mMainViewModel.getQuestionCardData().observe(this, questionCardDatas -> {
            mMainViewModel.setQuestionDataList(questionCardDatas);
            Collection<PieEntry> pieEntries = getItemPieEntries(questionCardDatas);
            mContainerPieView.setData(getPieData(pieEntries));
            mContainerPieView.setOnChartValueSelectedListener(this);
            mContainerPieView.setTransparentCircleRadius(58f);
            mContainerPieView.setHoleRadius(58f);
            mContainerPieView.setUsePercentValues(true);
            mObjectifAdapter.addItems(questionCardDatas);
            mObjectifAdapter.notifyDataSetChanged();
        });
    }

    /**
     * Permet de créer les element du camembert
     * @param questionCardDatas
     * @return
     */
    @NonNull
    private Collection<PieEntry> getItemPieEntries(@NonNull List<QuestionCardData> questionCardDatas) {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (QuestionCardData item : questionCardDatas) {
            PieEntry pieEntry = new PieEntry(5, item.question.questionText, item);
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

    private void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
