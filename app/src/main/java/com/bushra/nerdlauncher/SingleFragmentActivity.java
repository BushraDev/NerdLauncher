package com.bushra.nerdlauncher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

abstract public class SingleFragmentActivity extends AppCompatActivity
{
    protected abstract Fragment createFragment();
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        fm.beginTransaction()
                .replace(R.id.fragment_container,createFragment())
                .commit();

    }
}
