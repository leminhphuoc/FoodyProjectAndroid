package hcmute.edu.vn.nhom02.foodyproject.service;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.Restaurant;
import hcmute.edu.vn.nhom02.foodyproject.viewmodel.RestaurantList;

public class RecyclerViewAdapterResult extends RecyclerView.Adapter<RecyclerViewAdapterResult.MyViewHolder>{
    private Context mContext;
    private List<Restaurant> mData;

    public RecyclerViewAdapterResult(Context mContext, List<Restaurant> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_result,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_restaurant_name.setText(mData.get(position).getName());
        holder.tv_restaurant_address.setText(mData.get(position).getAddress());
        holder.tv_restaurant_type.setText(mData.get(position).getType());
        holder.img_restaurant_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, RestaurantList.class);

                //passing data to the book activity
                intent.putExtra("Name",mData.get(position).getName());
                intent.putExtra("Address",mData.get(position).getAddress());
                intent.putExtra("Typre",mData.get(position).getType());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                //start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_restaurant_name;
        TextView tv_restaurant_address;
        TextView tv_restaurant_type;
        ImageView img_restaurant_thumbnail;
        CardView cardview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_restaurant_name=(TextView) itemView.findViewById(R.id.restaurant_name_id);
            tv_restaurant_address=(TextView) itemView.findViewById(R.id.restaurant_address_id);
            tv_restaurant_type=(TextView) itemView.findViewById(R.id.restaurant_type_id);
            img_restaurant_thumbnail=(ImageView) itemView.findViewById(R.id.restaurant_img_id);
            cardview=(CardView) itemView.findViewById(R.id.cardviewResult_id);
        }
    }
}
