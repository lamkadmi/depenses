package com.mindorks.framework.mvvm.ui.feed;

import android.text.TextUtils;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableField;

/**
 * Created by Jyoti on 29/07/17.
 */

public class SpendingViewModel extends BaseViewModel {

    private final ObservableField<String> userName = new ObservableField<>();

    private final ObservableField<String> userEmail = new ObservableField<>();

    private final ObservableField<String> appVersion = new ObservableField<>();

    public SpendingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public void onNavMenuCreated() {
        final String currentUserName = getDataManager().getCurrentUserName();
        if (!TextUtils.isEmpty(currentUserName)) {
            userName.set(currentUserName);
        }

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (!TextUtils.isEmpty(currentUserEmail)) {
            userEmail.set(currentUserEmail);
        }
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }
}
