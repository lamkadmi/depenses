
package com.project.depense.mvvm.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.depense.mvvm.BR;
import com.project.depense.mvvm.R;
import com.project.depense.mvvm.ViewModelProviderFactory;
import com.project.depense.mvvm.databinding.ActivityLoginBinding;
import com.project.depense.mvvm.ui.base.BaseActivity;
import com.project.depense.mvvm.ui.home.SpendingActivity;
import com.project.depense.mvvm.utils.AppLogger;

import java.util.Arrays;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    private static final int RC_SIGN_IN = 9001;

    GoogleSignInClient mGoogleSignInClient;

    @Inject
    ViewModelProviderFactory factory;

    private LoginViewModel mLoginViewModel;

    private ActivityLoginBinding mActivityLoginBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        return mLoginViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isCurrentUserLogged()) {
            mLoginViewModel.synchroniseCategoriesFromFireStore();
            Intent intent = SpendingActivity.newIntent(LoginActivity.this);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void signup() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAlwaysShowSignInMethodScreen(true)
                        .setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_logo)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void openMainActivity() {
        Intent intent = SpendingActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                Toast.makeText(getApplicationContext(), "Signing Success", Toast.LENGTH_SHORT).show();
                mLoginViewModel.synchroniseCategoriesFromFireStore();
            } else { // ERRORS
                if (response == null) {
                    Toast.makeText(getApplicationContext(), "Signing error", Toast.LENGTH_SHORT).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), "NO_NETWORK", Toast.LENGTH_SHORT).show();
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getApplicationContext(), "UNKNOWN_ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        AppLogger.w("signInWithCredential:failure", user.getDisplayName());
    }

    protected Boolean isCurrentUserLogged() {
        return (this.getCurrentUser() != null);
    }

}
