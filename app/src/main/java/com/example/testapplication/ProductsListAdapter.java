package com.example.testapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder>{

    Context context;
    List<ProductModel> productModels;

    public ProductsListAdapter(Context context) {
        this.context = context;
        productModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout

        View notification_item = inflater.inflate(R.layout.dynamic_list_item,
                parent, false);

        ViewHolder viewHolder = new ViewHolder(notification_item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductsListAdapter.ViewHolder holder, int position) {
        holder.productName.setText(productModels.get(position).getTitle().toString());
        holder.productPrice.setText(productModels.get(position).getPrice().toString());
        holder.productDescription.setText(productModels.get(position).getDescription().toString());
        Glide.with(context)
                .load(productModels.get(position).getImageUrl())
                .into(holder.productImage);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public void setList(List<ProductModel> productModels) {
        this.productModels = productModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productPrice,productDescription;
        AppCompatImageView productImage;
        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
