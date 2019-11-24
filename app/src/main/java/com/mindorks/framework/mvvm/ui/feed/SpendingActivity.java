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

package com.mindorks.framework.mvvm.ui.feed;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.BuildConfig;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.databinding.ActivityFeedBinding;
import com.mindorks.framework.mvvm.databinding.NavHeaderMainBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.main.MainNavigator;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class SpendingActivity extends BaseActivity<ActivityFeedBinding, SpendingViewModel> implements MainNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    SpendingPagerAdapter mPagerAdapter;

    @Inject
    ViewModelProviderFactory factory;

    private ActivityFeedBinding mActivityFeedBinding;

    private DrawerLayout mDrawer;

    private NavigationView mNavigationView;

    private Toolbar mToolbar;

    private SpendingViewModel mSpendingViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, SpendingActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    public SpendingViewModel getViewModel() {
        mSpendingViewModel = ViewModelProviders.of(this,factory).get(SpendingViewModel.class);
        return mSpendingViewModel;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                Intent upIntent = NavUtils.getParentActivityIntent(this);
//                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
//                    // This activity is NOT part of this app's task, so create a new task
//                    // when navigating up, with a synthesized back stack.
//                    TaskStackBuilder.create(this)
//                            // Add all of this activity's parents to the back stack
//                            .addNextIntentWithParentStack(upIntent)
//                            // Navigate up to the closest parent
//                            .startActivities();
//                } else {
//                    // This activity is part of this app's task, so simply
//                    // navigate up to the logical parent activity.
//                    NavUtils.navigateUpTo(this, upIntent);
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFeedBinding = getViewDataBinding();
        mSpendingViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        mToolbar = mActivityFeedBinding.toolbar;
        mNavigationView = mActivityFeedBinding.navigationView;
        mDrawer = mActivityFeedBinding.drawerView;


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
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mSpendingViewModel.updateAppVersion(version);
        setupNavMenu();

        mSpendingViewModel.onNavMenuCreated();

        mPagerAdapter.setCount(3);

        mActivityFeedBinding.feedViewPager.setAdapter(mPagerAdapter);

        mActivityFeedBinding.tabLayout.addTab(mActivityFeedBinding.tabLayout.newTab().setText(getString(R.string.accueil)));
        mActivityFeedBinding.tabLayout.addTab(mActivityFeedBinding.tabLayout.newTab().setText(getString(R.string.detail)));
        mActivityFeedBinding.tabLayout.addTab(mActivityFeedBinding.tabLayout.newTab().setText(getString(R.string.revenu)));

        mActivityFeedBinding.feedViewPager.setOffscreenPageLimit(mActivityFeedBinding.tabLayout.getTabCount());

        mActivityFeedBinding.feedViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityFeedBinding.tabLayout));

        mActivityFeedBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityFeedBinding.feedViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openLoginActivity() {

    }

    private void setupNavMenu() {
        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, mActivityFeedBinding.navigationView, false);
        mActivityFeedBinding.navigationView.addHeaderView(navHeaderMainBinding.getRoot());
        navHeaderMainBinding.setViewModel(mSpendingViewModel);

        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.navItemAbout:
                            //showAboutFragment();
                            return true;
//                        case R.id.navItemRateUs:
//                            PrevisionDialog.newInstance().show(getSupportFragmentManager());
//                            return true;
                        case R.id.navItemFeed:
                            startActivity(SpendingActivity.newIntent(SpendingActivity.this));
                            return true;
                        case R.id.navItemLogout:
                            //mMainViewModel.logout();
                            return true;
                        default:
                            return false;
                    }
                });
    }
}
