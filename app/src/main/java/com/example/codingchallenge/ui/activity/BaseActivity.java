package com.example.codingchallenge.ui.activity;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

//    public void addFragment(@IdRes int containerViewId,
//                               @NonNull Fragment fragment,
//                               @NonNull String fragmentTag) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(containerViewId, fragment, fragmentTag)
//                .disallowAddToBackStack()
//                .commit();
//    }

    public void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag) {
        replaceFragment(containerViewId, fragment, fragmentTag, null);
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    @Override
    public void onBackPressed() {
        // if this is the last fragment then close the app
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
