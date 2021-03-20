package com.mindorks.framework.mvvm.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.BuildConfig;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.databinding.ActivitySpendingBinding;
import com.mindorks.framework.mvvm.databinding.NavHeaderMainBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.categorie.dialog.CategorieDialogCallback;
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

public class SpendingActivity extends BaseActivity<ActivitySpendingBinding, SpendingViewModel> implements MainNavigator,
        HasSupportFragmentInjector, CategorieDialogCallback {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    SpendingPagerAdapter mPagerAdapter;

    @Inject
    ViewModelProviderFactory factory;

    private ActivitySpendingBinding mActivitySpendingBinding;

    private DrawerLayout mDrawer;

    private NavigationView mNavigationView;

    private BottomNavigationView mBottomNavigationView;

    private Toolbar mToolbar;

    private BottomAppBar mBottomToolbar;

    private SpendingViewModel mSpendingViewModel;

    private DashboardFragmentListener dashboardFragmentListener;

    private RevenuFragmentListener revenuFragmentListener;


    public static Intent newIntent(Context context) {
        return new Intent(context, SpendingActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_spending;
    }

    @Override
    public SpendingViewModel getViewModel() {
        mSpendingViewModel = ViewModelProviders.of(this, factory).get(SpendingViewModel.class);
        return mSpendingViewModel;
    }

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
            case R.id.action_home:
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
        mActivitySpendingBinding = getViewDataBinding();
        mSpendingViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        mToolbar = mActivitySpendingBinding.toolbar;
        mBottomToolbar = mActivitySpendingBinding.bottomAppBar;
        mNavigationView = mActivitySpendingBinding.navigationView;
        mBottomNavigationView = mActivitySpendingBinding.bottomNavigation;

        mDrawer = mActivitySpendingBinding.drawerView;

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
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mSpendingViewModel.updateAppVersion(version);
        setupNavMenu();

        mSpendingViewModel.onNavMenuCreated();

        mPagerAdapter.setCount(2);

        mActivitySpendingBinding.feedViewPager.setAdapter(mPagerAdapter);

        mActivitySpendingBinding.tabLayout.addTab(mActivitySpendingBinding.tabLayout.newTab().setText(getString(R.string.accueil)));

        mActivitySpendingBinding.tabLayout.addTab(mActivitySpendingBinding.tabLayout.newTab().setText(getString(R.string.revenu)));

        mActivitySpendingBinding.feedViewPager.setOffscreenPageLimit(mActivitySpendingBinding.tabLayout.getTabCount());

        mActivitySpendingBinding.feedViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivitySpendingBinding.tabLayout));

        mActivitySpendingBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivitySpendingBinding.feedViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });

        mActivitySpendingBinding.btnAdd.setOnClickListener(v -> {
            if(mActivitySpendingBinding.tabLayout.getSelectedTabPosition()==0){
                dashboardFragmentListener.onDepenseAdd();
            }else{
                revenuFragmentListener.onRevenuAdd();
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
                R.layout.nav_header_main, mActivitySpendingBinding.navigationView, false);

        mActivitySpendingBinding.navigationView.addHeaderView(navHeaderMainBinding.getRoot());

        navHeaderMainBinding.setViewModel(mSpendingViewModel);

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            mActivitySpendingBinding.feedViewPager.setCurrentItem(0);
                            return true;
                        case R.id.action_revenu:
                            mActivitySpendingBinding.feedViewPager.setCurrentItem(1);
                            return true;
                        case R.id.navItemLogout:
                            return true;
                        default:
                            return false;
                    }
                });
    }


    @Override
    public void updateDashboard() {
        mPagerAdapter.notifyDataSetChanged();
    }

    public interface DashboardFragmentListener {
        void onDepenseAdd();
    }

    public interface RevenuFragmentListener {
        void onRevenuAdd();
    }

    public void setDashboardFragmentListener(DashboardFragmentListener dashboardFragmentListener) {
        this.dashboardFragmentListener = dashboardFragmentListener;
    }

    public void setRevenuFragmentListener(RevenuFragmentListener revenuFragmentListener) {
        this.revenuFragmentListener = revenuFragmentListener;
    }
}
