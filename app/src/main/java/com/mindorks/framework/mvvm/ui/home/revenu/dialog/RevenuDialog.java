package com.mindorks.framework.mvvm.ui.home.revenu.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.databinding.DialogRevenuBinding;
import com.mindorks.framework.mvvm.ui.base.BaseDialog;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

public class RevenuDialog extends BaseDialog implements RevenuDialogNavigator {

    private static final String TAG = RevenuDialog.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    private RevenuDialogViewModel mRevenuDialogViewModel;

    private RevenuDialogCallback listener;

    private DialogRevenuBinding mRevenuDialogBinding;

    public static RevenuDialog newInstance() {
        Bundle args = new Bundle();
        RevenuDialog fragment = new RevenuDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRevenuDialogBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_revenu, container, false);
        View view = mRevenuDialogBinding.getRoot();

        AndroidSupportInjection.inject(this);

        mRevenuDialogViewModel = ViewModelProviders.of(this,factory).get(RevenuDialogViewModel.class);

        mRevenuDialogBinding.setViewModel(mRevenuDialogViewModel);
        mRevenuDialogViewModel.setNavigator(this);

        return view;
    }

    public void setListener(RevenuDialogCallback listener) {
        this.listener = listener;
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
        listener.updateListeRevenu();
    }
}
