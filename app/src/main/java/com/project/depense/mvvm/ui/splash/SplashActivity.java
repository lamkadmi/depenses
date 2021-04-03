
package com.project.depense.mvvm.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.project.depense.mvvm.BR;
import com.project.depense.mvvm.R;
import com.project.depense.mvvm.ViewModelProviderFactory;

import com.project.depense.mvvm.databinding.ActivitySplashBinding;
import com.project.depense.mvvm.ui.base.BaseActivity;
import com.project.depense.mvvm.ui.home.SpendingActivity;
import com.project.depense.mvvm.ui.login.LoginActivity;
import com.project.depense.mvvm.ui.main.MainActivity;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator {

    @Inject
    ViewModelProviderFactory factory;

    private SplashViewModel mSplashViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        mSplashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        return mSplashViewModel;
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openMainActivity() {
        Intent intent = SpendingActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openDashBoard() {
        Intent intent = SpendingActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel.setNavigator(this);
        mSplashViewModel.startDashboard();
    }
}
