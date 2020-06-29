package hcmute.edu.vn.nhom02.foodyproject.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;

public class RestaurantImageAdapter extends RecyclerView.Adapter<RestaurantImageAdapter.ImageViewHolder>  {
    private Context mContext;
    private List<Integer> mData;

    public RestaurantImageAdapter(Context mContext, List<Integer>  mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RestaurantImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_restaurant_image,parent,false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantImageAdapter.ImageViewHolder holder, int position) {
        holder.img_food.setImageResource(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView img_food;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img_food=(ImageView) itemView.findViewById(R.id.restaurant_image);
        }
    }
}
