package hcmute.edu.vn.nhom02.foodyproject.service;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.FoodInMenu;

public class MenuListAdapter  extends ArrayAdapter<FoodInMenu>  {
    private ArrayList<FoodInMenu> dataSet;

    private static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
    }

    Context mContext;
    public MenuListAdapter(ArrayList<FoodInMenu> data, @NonNull Context context) {
        super(context, R.layout.food_item_menu, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FoodInMenu dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.food_item_menu, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.type);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
////        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getFoodName());
        viewHolder.txtPrice.setText(dataModel.getPrice().toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
