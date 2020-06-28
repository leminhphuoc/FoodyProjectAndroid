package hcmute.edu.vn.nhom02.foodyproject;

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
        holder.img_food.setImageResource(mData.get(position).getImage());
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
