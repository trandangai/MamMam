package com.khtn.mammam.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.khtn.mammam.R;
import com.khtn.mammam.pojo.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by User on 2/11/2017.
 */

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> commentList = new ArrayList<>();
    private static LayoutInflater inflater = null;

    public CommentAdapter(Activity activity, List<Comment> commentList) {
        this.context = activity;
        this.commentList = commentList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder {
        TextView txtCommenter;
        TextView txtCommentContent;
        ImageView imgIconComment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.commentitemlayout, null);
        holder.imgIconComment = (ImageView) rowView.findViewById(R.id.imgIconCommenter);
        holder.txtCommenter = (TextView) rowView.findViewById(R.id.txtCommenter);
        holder.txtCommentContent = (TextView) rowView.findViewById(R.id.txtCommentContent);

        holder.txtCommenter.setText(commentList.get(position).getUserName().toString());
        holder.txtCommentContent.setText(commentList.get(position).getUserComment().toString());

        String fistChar = commentList.get(position).getUserName().substring(0, 1);
        TextDrawable drawable = TextDrawable.builder().buildRound(fistChar, Color.RED);
        holder.imgIconComment.setImageDrawable(drawable);
        return rowView;
    }
}
