package com.mindorks.framework.mvvm.ui.categorie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.databinding.ActivityCategorieBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.categorie.dialog.CategorieDialog;
import com.mindorks.framework.mvvm.ui.categorie.dialog.CategorieDialogCallback;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class CategorieActivity extends BaseActivity<ActivityCategorieBinding, CategorieViewModel>
        implements CategorieNavigator, HasSupportFragmentInjector, CategorieAdapter.CategorieAdapterListener,
        CategorieDialogCallback {

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    LinearLayoutManager mLayoutManager;

    private CategorieViewModel mCategorieViewModel;

    private ActivityCategorieBinding mActivityCategorieBinding;

    @Inject
    CategorieAdapter mCategoriesAdapter;


    public static Intent newIntent(Context context) {
        return new Intent(context, CategorieActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_categorie;
    }

    @Override
    public CategorieViewModel getViewModel() {
        mCategorieViewModel = ViewModelProviders.of(this, factory).get(CategorieViewModel.class);
        return mCategorieViewModel;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCategorieBinding = getViewDataBinding();
        mCategorieViewModel.setNavigator(this);
        mCategoriesAdapter.setListener(this);
        setUp();
    }

    private void setUp() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mActivityCategorieBinding.categories.setLayoutManager(mLayoutManager);
        mActivityCategorieBinding.categories.setItemAnimator(new DefaultItemAnimator());
        mActivityCategorieBinding.categories.setAdapter(mCategoriesAdapter);
       /* mActivityCategorieBinding.addCategorie.setOnClickListener(v -> {
            CategorieDialog dialog = CategorieDialog.newInstance();
            dialog.setListener(this);
            dialog.show(getSupportFragmentManager());
        });*/
    }


    @Override
    public void onRetryClick() {
        mCategorieViewModel.fetchCategories();
    }

    @Override
    public void updateDashboard() {
        mCategorieViewModel.fetchCategories();
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
