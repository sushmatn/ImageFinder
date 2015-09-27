package com.instagramclient.sushmanayak.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.instagramclient.sushmanayak.gridimagesearch.R;
import com.instagramclient.sushmanayak.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SushmaNayak on 9/21/2015.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    private static class ViewHolder
    {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvDimension;
    }

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, 0, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageResult = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDimension= (TextView)convertView.findViewById(R.id.tvDimension);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        viewHolder.ivImage.setImageResource(0);
        Picasso.with(getContext()).load(imageResult.getThumbURL()).into(viewHolder.ivImage);
        viewHolder.tvTitle.setText(Html.fromHtml(imageResult.getTitle()));
        viewHolder.tvDimension.setText(imageResult.getWidth() + " x " + imageResult.getHeight());
        return convertView;
    }
}
