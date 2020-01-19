package com.bushra.nerdlauncher;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NerdLauncherFragment extends Fragment
{

    private RecyclerView mRecyclerView;

    public static Fragment newInstance()
    {
        return new NerdLauncherFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nerd_launcher, container, false);
        mRecyclerView = v.findViewById(R.id.app_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return v;

    }


    private class AppHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;
        private ImageView mIconImageView;
        public AppHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.app_name);
            mIconImageView=itemView.findViewById(R.id.app_icon);
            itemView.setOnClickListener(this);

        }
        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
            mIconImageView.setImageDrawable(mResolveInfo.loadIcon(pm));
        }

        @Override
        public void onClick(View view) {
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            Intent i = new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,
                            activityInfo.name);
            startActivity(i);
        }
    }

    private class ActivityAdapter extends RecyclerView.Adapter<AppHolder> {
        private final List<ResolveInfo> mActivities;
        public ActivityAdapter(List<ResolveInfo> activities)
        {
            mActivities = activities;
        }
        @Override
        public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_app, parent, false);
            return new AppHolder(view);
        }
        @Override
        public void onBindViewHolder(AppHolder holder, int position) {
            ResolveInfo resolveInfo = mActivities.get(position);
            holder.bindActivity(resolveInfo);
        }
        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }



    private void setupAdapter() {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);
        mRecyclerView.setAdapter(new ActivityAdapter(activities));

    }


}
