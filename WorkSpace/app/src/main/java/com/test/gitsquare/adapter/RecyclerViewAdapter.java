package com.test.gitsquare.adapter;

/**
 * Created by deep on 26-Apr-17.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.gitsquare.MainActivity;
import com.test.gitsquare.R;
import com.test.gitsquare.pojo.Data;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JUNED on 6/16/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ProjectViewHolder> {
    private List<Data> dataArrayList;
    MainActivity mainActivity;


    public static class ProjectViewHolder extends RecyclerView.ViewHolder {

        public TextView login, repos_url, contributions;

        public CircleImageView avatar_url;

        ProjectViewHolder(View v) {
            super(v);
            login = (TextView) v.findViewById(R.id.login);
            repos_url = (TextView) v.findViewById(R.id.reposurl);
            contributions = (TextView) v.findViewById(R.id.contributions);
            avatar_url = (CircleImageView) v.findViewById(R.id.avatarurl);


        }


    }

    public RecyclerViewAdapter(List<Data> dataArrayList, MainActivity mainActivity) {
        this.dataArrayList = dataArrayList;
        this.mainActivity = mainActivity;

    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ProjectViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(final ProjectViewHolder Vholder, final int position) {

        Data data = dataArrayList.get(position);

        Glide.with(mainActivity)
                .load(data.getAvatar_url())
                .into(Vholder.avatar_url);

        Vholder.login.setText(data.getLogin());
        Vholder.repos_url.setText(data.getRepos_url());
        Vholder.contributions.setText(mainActivity.getText(R.string.contributions)+": "+String.valueOf(data.getContributions()));

    }

    @Override
    public int getItemCount() {

        return dataArrayList.size();
    }


}
