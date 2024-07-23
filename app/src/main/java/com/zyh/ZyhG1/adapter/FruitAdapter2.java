package com.zyh.ZyhG1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.model.Fruit;

import java.util.List;

public class FruitAdapter2 extends RecyclerView.Adapter<FruitAdapter2.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView _fruitImage;
        TextView _fruitName;
        public ViewHolder(View view) {
            super(view);
            _fruitImage = view.findViewById(R.id.fruit_image);
            _fruitName = view.findViewById(R.id.fruit_name);
        }
    }

    List<Fruit> _fruitList;

    public FruitAdapter2(List<Fruit> fruitList) {
        _fruitList = fruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(view1 -> {
            int position = viewHolder.getAdapterPosition();
            Fruit fruit = _fruitList.get(position);
            Toast.makeText(parent.getContext(), "You clicked view " + fruit._name,
                    Toast.LENGTH_SHORT).show();
        });
        viewHolder._fruitImage.setOnClickListener(view2 -> {
            int position = viewHolder.getAdapterPosition();
            Fruit fruit = _fruitList.get(position);
            Toast.makeText(parent.getContext(), "You clicked image " + fruit._name,
                    Toast.LENGTH_SHORT).show();
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = _fruitList.get(position);
        holder._fruitImage.setImageResource(fruit._imgId);
        holder._fruitName.setText(fruit._name);
    }

    @Override
    public int getItemCount() {
        return _fruitList.size();
    }
}
