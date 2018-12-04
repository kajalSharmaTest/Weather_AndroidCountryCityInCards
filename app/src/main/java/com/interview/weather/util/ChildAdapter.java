package com.interview.weather.util;

/**
 * Created by P7111463 on 7/6/2018.
 */

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.interview.weather.R;

import java.util.ArrayList;

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> childData;
    private ArrayList<String> childDataBk;

    public ChildAdapter(ArrayList<String> childData) {
        this.childData = childData;
        childDataBk = new ArrayList<>(childData);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       TextView tvChild;

        public ViewHolder(View itemView) {
            super(itemView);
            tvChild = itemView.findViewById(R.id.tv_child);
            //bind(this, itemView);
        }

    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nested_recycler_item_child, parent, false);
        ChildAdapter.ViewHolder cavh = new ChildAdapter.ViewHolder(itemLayoutView);
        return cavh;
    }


    final Handler handler = new Handler();
    Runnable collapseList = new Runnable() {
        @Override
        public void run() {
            if (getItemCount() > 1) {
                childData.remove(1);
                notifyDataSetChanged();
                handler.postDelayed(collapseList, 50);
            }
        }
    };

    Runnable expandList = new Runnable() {
        @Override
        public void run() {
            int currSize = childData.size();
            if (currSize == childDataBk.size()) return;

            if (currSize == 0) {
                childData.add(childDataBk.get(currSize));
            } else {
                childData.add(childDataBk.get(currSize));
            }
            notifyDataSetChanged();

            handler.postDelayed(expandList, 50);
        }
    };


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;


        String city = childData.get(position);
        vh.tvChild.setText(city);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemCount() > 1) {
                    handler.post(collapseList);
                } else {
                    handler.post(expandList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return childData.size();
    }
}