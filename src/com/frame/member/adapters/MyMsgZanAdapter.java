package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.R;
import com.frame.member.bean.MainVideoBean.MainVideoCategory;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;
/**
 * 消息通知-评论适配器
 * @author Ron
 * @date 2016-8-20  上午12:14:33
 */
public class MyMsgZanAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<String> mAppList;
	public MyMsgZanAdapter(Context context,
			 List<String> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public String getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_my_msg_zan, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        String item = getItem(position);
//        holder.my_collect_class_img_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "iv_icon_click", Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.my_collect_class_text_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"iv_icon_click",Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }


    @Override
    public boolean getSwipEnableByPosition(int position) {
        if(position % 2 == 0){
            return false;
        }
        return true;
    }

    class ViewHolder {
        ImageView my_collect_class_img_1;
        TextView my_collect_class_text_1,my_collect_class_text_2,my_collect_class_text_3,
        my_collect_class_text_4,my_collect_class_text_5,my_collect_class_text_6,
        my_collect_class_text_7;
        RatingBar my_collect_ratingBar1;

        public ViewHolder(View view) {
            view.setTag(this);
        }
    }
}
