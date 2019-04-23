package com.luoares.plan;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import static com.luoares.plan.MainActivity.writePlan;

public class PlanAdapter extends ArrayAdapter<ListInformation> {
    private Activity mContext = null; // 上下文环境
    private int mResourceId; // 列表项布局资源ID
    private List<ListInformation> mItems; // 列表内容数组

    public PlanAdapter(Activity context, int resId, List<ListInformation> items) {
        super(context, resId, items);
        mContext = context;
        mResourceId = resId;
        mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListInformation listInformation = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.plan = (TextView) view.findViewById(R.id.list_plan);
            viewHolder.finsih = (CheckBox) view.findViewById(R.id.list_finish);
            viewHolder.finsih.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if(isChecked){
                        listInformation.setChecked(true);
                    }else{
                        listInformation.setChecked(false);
                    }
                    writePlan();
                }
            });
            if(viewHolder.plan.getCurrentTextColor() == Color.RED) {
                viewHolder.type = 0;
            } else if(viewHolder.plan.getCurrentTextColor() == Color.GREEN) {
                viewHolder.type = 1;
            } else if(viewHolder.plan.getCurrentTextColor() == Color.BLUE) {
                viewHolder.type = 2;
            } else {
                viewHolder.type = 3;
            }
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.plan.setText(listInformation.getPlan());
        if(listInformation.getStyle() == 0) {
            viewHolder.plan.setTextColor(Color.RED);
            viewHolder.finsih.setTextColor(Color.RED);
        } else if(listInformation.getStyle() == 1) {
            viewHolder.plan.setTextColor(Color.GREEN);
            viewHolder.finsih.setTextColor(Color.GREEN);
        } else if(listInformation.getStyle() == 2) {
            viewHolder.plan.setTextColor(Color.BLUE);
            viewHolder.finsih.setTextColor(Color.BLUE);
        } else {
            viewHolder.plan.setTextColor(Color.BLACK);
            viewHolder.finsih.setTextColor(Color.BLACK);
        }
        viewHolder.finsih.setChecked(listInformation.getChecked());
        return view;
    }

    class ViewHolder {
        TextView plan;
        CheckBox finsih;
        Integer type;
    }
}
