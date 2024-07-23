package com.zyh.ZyhG1.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.model.Fruit;
import com.zyh.ZyhG1.ulit.ConvertHelper;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit>{
    Activity _activity;
    int _resourceId;
    List<Fruit> _data;

    public static class ViewHolder {
        ImageView _fruitImage;
        TextView _fruitName;
        public ViewHolder(ImageView fruitImage, TextView fruitName) {
            _fruitImage = fruitImage;
            _fruitName = fruitName;
        }
    }

    public FruitAdapter(Activity activity, int resourceId, List<Fruit> data) {
        super(activity, resourceId, data);
        _activity = activity;
        _resourceId = resourceId;
        _data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(_resourceId, parent, false);
            ImageView fruitImage = view.findViewById(R.id.fruit_image);
            TextView fruitName = view.findViewById(R.id.fruit_name);
            viewHolder = new ViewHolder(fruitImage, fruitName);
        } else {
            view = convertView;
            viewHolder = ConvertHelper.GetConvert(view.getTag(), ViewHolder.class);
        }
        Fruit fruit = getItem(position);
        if (fruit != null && viewHolder != null) {
            viewHolder._fruitImage.setImageResource(fruit._imgId);
            viewHolder._fruitName.setText(fruit._name);
        }
        // return super.getView(position, convertView, parent);
        return view;
    }
}
