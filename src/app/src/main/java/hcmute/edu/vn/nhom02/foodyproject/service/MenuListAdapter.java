package hcmute.edu.vn.nhom02.foodyproject.service;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.TreeSet;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.FoodInMenu;

public class MenuListAdapter  extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private ArrayList<FoodInMenu> mData = new ArrayList<FoodInMenu>();
    private ArrayList<FoodInMenu> mTempData = new ArrayList<FoodInMenu>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public MenuListAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
    }

    public void addItem(final FoodInMenu item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final FoodInMenu item) {
        mData.add(item);
        mTempData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public FoodInMenu getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int lastPosition = -1;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FoodInMenu dataModel = getItem(position);
        int rowType = getItemViewType(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.food_item_menu, null);
                    viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
                    viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.price);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.header_food_menu, null);
                    viewHolder.txtName = (TextView) convertView.findViewById(R.id.headerText);
                    viewHolder.txtName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                                       if(!mData.isEmpty())
//                            {
//                                mData.clear();
//                                notifyDataSetChanged();
//                            }
//                            else
//                            {
//                                mData = mTempData;
//                                notifyDataSetChanged();
//                            }
                        }
                    });
                    break;
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtName.setText(mData.get(position).getFoodName());
        if(viewHolder.txtPrice != null)
        {
            viewHolder.txtPrice.setText(String.valueOf(mData.get(position).getPrice()));
        }


        return convertView;
    }
}
