package hcmute.edu.vn.nhom02.foodyproject.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.Food;

public class RecylerFoodAdapter extends RecyclerView.Adapter<RecylerFoodAdapter.FoodViewHolder>  {

    private Context mContext;
    private List<Food> mData;

    public RecylerFoodAdapter(Context mContext, List<Food> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_food,parent,false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.tv_food_name.setText(mData.get(position).getName());
        Picasso.with(mContext).load(mData.get(position).getImage()).resize(180,160).into(holder.img_food);
        //holder.img_food.setImageResource(mData.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView tv_food_name;
        ImageView img_food;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_food_name=(TextView) itemView.findViewById(R.id.food_name_id);
            img_food=(ImageView) itemView.findViewById(R.id.food_img_id);
        }
    }
}
