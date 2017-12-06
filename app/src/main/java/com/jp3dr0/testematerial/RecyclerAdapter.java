package com.jp3dr0.testematerial;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List itemModels;
    private Context context;
    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;
    private boolean isSwitchView = true;

    public RecyclerAdapter(Context context, List itemModels) {
        this.itemModels = itemModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView;
        if (i == LIST_ITEM){
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.item_layout, null);
        }else{
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout_grid, null);
        }
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemModel model = (ItemModel) itemModels.get(position);
        initializeViews(model, holder, position);
    }

    @Override
    public int getItemViewType (int position) {
        if (isSwitchView){
            return LIST_ITEM;
        }else{
            return GRID_ITEM;
        }
    }

    public boolean toggleItemViewType () {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }



    private void initializeViews(ItemModel model, final RecyclerView.ViewHolder holder, int position) {

        ((ItemViewHolder)holder).name.setText(model.getName());


        String imageUrl = model.getImagePath();
        if (imageUrl != null && !imageUrl.isEmpty()){
            Glide.with(context).load(imageUrl).apply(new RequestOptions().placeholder(R.drawable.loading)).into(((ItemViewHolder)holder).imageView);
            //Glide.with(context).load(imageUrl).centerCrop().placeholder(R.drawable.loading_spinner).into((ItemViewHolder)holder).imageView);
            Log.i("LOG", "eae");
        }
        else {
            ((ItemViewHolder)holder).imageView.setImageResource( model.getPhoto() );
            Log.i("LOG", "rtf");
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.imageView)
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}