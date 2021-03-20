package com.project.depense.mvvm.ui.home.depense.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mindorks.framework.mvvm.R;
import com.project.depense.mvvm.ViewModelProviderFactory;
import com.project.depense.mvvm.data.model.db.Categorie;
import com.mindorks.framework.mvvm.databinding.DialogDepenseBinding;
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

public class DepenseDialog extends BaseDialog implements DepenseDialogNavigator {

    private static final String TAG = DepenseDialog.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    private DepenseDialogViewModel mDepenseDialogViewModel;

    private DepenseDialogCallback listener;

    private DialogDepenseBinding mDepenseDialogBinding;

    public static DepenseDialog newInstance() {
        Bundle args = new Bundle();
        DepenseDialog fragment = new DepenseDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDepenseDialogBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_depense, container, false);
        View view = mDepenseDialogBinding.getRoot();

        AndroidSupportInjection.inject(this);

        mDepenseDialogViewModel = ViewModelProviders.of(this, factory).get(DepenseDialogViewModel.class);

        mDepenseDialogBinding.setViewModel(mDepenseDialogViewModel);
        mDepenseDialogViewModel.setNavigator(this);

        setUp();

        return view;
    }

    private void setUp() {
        subscribeToLiveData();
        mDepenseDialogBinding.champDateValeur.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener listener = (v1, year, monthOfYear, dayOfMonth) -> {
                String data = AppUtils.getDate(year, monthOfYear, dayOfMonth);
                mDepenseDialogBinding.champDateValeur.setText(data);
            };
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            DatePickerDialog dpDialog = new DatePickerDialog(this.getContext(), listener, cal.get(1), cal.get(2), cal.get(5));
            dpDialog.show();

        });
    }

    private void subscribeToLiveData() {
        mDepenseDialogViewModel.getCategorieListLiveData().observe(this, mDatas -> {
            ArrayAdapter<Categorie> dataAdapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, mDatas);
            mDepenseDialogBinding.categorie.setAdapter(dataAdapter);
        });
    }

    public void setListener(DepenseDialogCallback listener) {
        this.listener = listener;
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
        listener.updateListeDepenses();
    }
}
