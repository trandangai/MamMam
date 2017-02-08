package com.khtn.mammam;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.khtn.mammam.pojo.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 1/5/2017.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Restaurant> restList = new ArrayList<>();
    private static LayoutInflater inflater = null;

    public MyAdapter(Activity Activity, List<Restaurant> restList) {
        // TODO Auto-generated constructor stub
        this.context = Activity;
        this.restList = restList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return restList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void swapItems(List<Restaurant> restList) {
        this.restList.clear();
        this.restList.addAll(restList);
        notifyDataSetChanged();
    }

    private class Holder {
        TextView tvRestName;
        TextView tvRestAddr;
        TextView tvTopCommenter;
        TextView tvRatingPoint;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        final View rowView;

        rowView = inflater.inflate(R.layout.griditemlayout, null);
        holder.tvRestName = (TextView) rowView.findViewById(R.id.tvRestName);
        holder.tvRestAddr = (TextView) rowView.findViewById(R.id.tvRestAddr);
        holder.tvTopCommenter = (TextView) rowView.findViewById(R.id.tvTopCommenter);
        holder.tvRatingPoint = (TextView) rowView.findViewById(R.id.txtPointRating);
        holder.img = (ImageView) rowView.findViewById(R.id.imgGridItem);

        //holder.tv.setText(result[position]);
        //holder.img.setImageResource(imageId[position]);
        //Log.d("img link", restList.get(position).getRestImg() + "");
        //Log.d("img name", restList.get(position).getRestName() + "");
        //Log.d("img addr", restList.get(position).getRestAddr() + "");
        //Log.d("lat", restList.get(position).getLatitude() + "");
        //Log.d("lng", restList.get(position).getLongitude() + "");
        //Log.d("rest details link", restList.get(position).getRestDetailLink() + "");
        //Log.d("score", restList.get(position).getRating().getScore() + "");
        //Log.d("numofUser", restList.get(position).getRating().getNumOfUser() + "");

        Picasso.with(context).load(restList.get(position).getRestImg()).into(holder.img);
        holder.tvRestName.setText(restList.get(position).getRestName());
        holder.tvRestAddr.setText(restList.get(position).getRestAddr());
        holder.tvTopCommenter.setText(restList.get(position).getRestTopCommenter().split(";")[0]);

        double ratingpoint = 0;
        int score = restList.get(position).getRating().getScore();
        if (score == 0) {
            ratingpoint = 0;
            holder.tvRatingPoint.setText(ratingpoint+"");
        } else {
            int numofUserComment = restList.get(position).getRating().getNumOfUser();
            String strDouble = String.format("%.1f", (double)score/numofUserComment);
            holder.tvRatingPoint.setText(strDouble);
        }

        return rowView;
    }
}
