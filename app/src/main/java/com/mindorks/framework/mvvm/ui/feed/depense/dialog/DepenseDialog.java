package com.mindorks.framework.mvvm.ui.feed.depense.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kal.rackmonthpicker.RackMonthPicker;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ViewModelProviderFactory;
import com.mindorks.framework.mvvm.data.model.db.Categorie;
import com.mindorks.framework.mvvm.databinding.DialogDepenseBinding;
import com.mindorks.framework.mvvm.ui.base.BaseDialog;

import java.util.Locale;

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

        mDepenseDialogViewModel = ViewModelProviders.of(this,factory).get(DepenseDialogViewModel.class);

        mDepenseDialogBinding.setViewModel(mDepenseDialogViewModel);
        mDepenseDialogViewModel.setNavigator(this);

        setUp();

        return view;
    }

    private void setUp(){
        subscribeToLiveData();
        mDepenseDialogBinding.champDateValeur.setOnClickListener(view -> new RackMonthPicker(getContext())
                .setLocale(Locale.FRANCE)
                .setPositiveButton((month, startDate, endDate, year, monthLabel) -> {
                    String date = month+"/"+year;
                    mDepenseDialogBinding.champDateValeur.setText(date);
                })
                .setNegativeButton(dialog -> {
                    dialog.dismiss();
                }).show());
    }

    private void subscribeToLiveData() {
        mDepenseDialogViewModel.getCategorieListLiveData().observe(this, mDatas -> {
            ArrayAdapter<Categorie> dataAdapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item,mDatas);
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
