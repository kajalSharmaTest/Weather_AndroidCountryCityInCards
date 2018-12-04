package com.interview.weather.util;

/**
 * Created by P7111463 on 7/6/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interview.weather.Country;
import com.interview.weather.R;

import java.util.ArrayList;

public class ParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Country> parentChildData;
    Context ctx;

    public ParentAdapter(Context ctx, ArrayList<Country> parentChildData) {
        this.ctx = ctx;
        this.parentChildData = parentChildData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_child;
        TextView countryName ;

        public ViewHolder(View itemView) {
            super(itemView);
            rv_child = itemView.findViewById(R.id.rv_child);
            countryName = itemView.findViewById(R.id.parent_title);
           // ButterKnife.bind(this, itemView);
        }
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_recycler_item_parent, parent, false);
        ParentAdapter.ViewHolder pavh = new ParentAdapter.ViewHolder(itemLayoutView);
        return pavh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Country p = parentChildData.get(position);
        vh.countryName.setText(p.getCountryName());
        initChildLayoutManager(vh.rv_child, p.getCity());
    }

    private void initChildLayoutManager(RecyclerView rv_child, ArrayList<String> childData) {
        rv_child.setLayoutManager(new NestedRecyclerLinearLayoutManager(ctx));
        ChildAdapter childAdapter = new ChildAdapter(childData);
        rv_child.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return parentChildData.size();
    }
}