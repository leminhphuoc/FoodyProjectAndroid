package hcmute.edu.vn.nhom02.foodyproject.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.nhom02.foodyproject.R;
import hcmute.edu.vn.nhom02.foodyproject.model.Province;
import hcmute.edu.vn.nhom02.foodyproject.viewmodel.RestaurantList;

public class RecyclerViewAdapterProvince extends RecyclerView.Adapter<RecyclerViewAdapterProvince.MyViewHolder>{

    private Context mContext;
    private List<Province> mData;

    public RecyclerViewAdapterProvince(Context mContext, List<Province> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_province,parent,false);

        return new RecyclerViewAdapterProvince.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Province objIncome = mData.get(position);
        holder.tv_province_name.setText(mData.get(position).getName());
        holder.cb_province.setOnCheckedChangeListener(null);
        holder.cb_province.setChecked(false);

        holder.cb_province.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                holder.cb_province.setChecked(isChecked);
                if (isChecked){
                    holder.tv_province_name.setTextColor(Color.BLUE);
                    Intent intent = new Intent(mContext, RestaurantList.class);

                    //passing data to the book activity
                    intent.putExtra("Noteee",mData.get(position).getNote());
                    intent.putExtra("ProNameee",mData.get(position).getName());
                    //start the activity
                    mContext.startActivity(intent);
                }
                else {
                    holder.tv_province_name.setTextColor(Color.parseColor("#2d2d2d"));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_province_name;
        RadioButton cb_province;
        CardView cardview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_province_name=(TextView) itemView.findViewById(R.id.province_name_id);
            cb_province=(RadioButton) itemView.findViewById(R.id.checkbox_province);
            cardview=(CardView) itemView.findViewById(R.id.cardviewProvince_id);
        }
    }
}
