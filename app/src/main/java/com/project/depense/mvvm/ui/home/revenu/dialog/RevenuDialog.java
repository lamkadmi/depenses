package com.project.depense.mvvm.ui.home.revenu.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.DialogRevenuBinding;
import com.project.depense.mvvm.ViewModelProviderFactory;
import com.project.depense.mvvm.ui.base.BaseDialog;
import com.project.depense.mvvm.utils.AppUtils;

import java.util.Calendar;
import java.util.TimeZone;

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

        mRevenuDialogViewModel = ViewModelProviders.of(this, factory).get(RevenuDialogViewModel.class);

        mRevenuDialogBinding.setViewModel(mRevenuDialogViewModel);
        mRevenuDialogViewModel.setNavigator(this);

/*       mRevenuDialogBinding.revenuDate.setOnClickListener(pView -> new RackMonthPicker(getContext())
                .setLocale(Locale.FRANCE)
                .setPositiveButton((month, startDate, endDate, year, monthLabel) -> {
                    String date = month + "/" + year;
                    mRevenuDialogBinding.revenuDate.setText(date);
                    // mRevenuDialogViewModel.sfetchPrevisionsByDate(date);
                })
                .setNegativeButton(dialog -> {
                    dialog.dismiss();
                }).show());*/

        mRevenuDialogBinding.revenuDate.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener listener = (v1, year, monthOfYear, dayOfMonth) -> {
                String data = AppUtils.getDate(year, monthOfYear, dayOfMonth);
                mRevenuDialogBinding.revenuDate.setText(data);
            };
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            DatePickerDialog dpDialog = new DatePickerDialog(this.getContext(), listener, cal.get(1), cal.get(2), cal.get(5));
            dpDialog.show();
        });

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
