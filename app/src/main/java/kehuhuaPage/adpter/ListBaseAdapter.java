package kehuhuaPage.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jauker.widget.BadgeView;

import java.util.List;
import java.util.Map;

import razerdp.friendcircle.R;


/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class ListBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<Map<String, Object>> mDatas;
    private Context mContext;

//    CountBadge.Factory circleFactory;


    public OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public ListBaseAdapter(List<Map<String, Object>> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载数据item的布局，生成VH返回
        View v = LayoutInflater.from(mContext).inflate(R.layout.cont_teacher_chat_listitem, parent, false);
        v.setOnClickListener(this);
        return new ComViewHolder(v);
    }

    private static final String TAG = "ListBaseAdapter";

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder thisholder, int position) {
        if (thisholder instanceof ComViewHolder) {
            final ComViewHolder holder = (ComViewHolder) thisholder;
            holder.setIsRecyclable(false);//禁止viewHolder复用
            Map<String, Object> map = getData(position);
            Log.e(TAG, "onBindViewHolder: map " + map.toString());
            //会话图片
//            String channelImage1 = String.valueOf(map.get(channelImageUrl));
//            String channelImage = "http://bailitianxia-edus.oss-cn-beijing.aliyuncs.com/2016-12-23/8765f3d3572c11df71c5ef7d652762d0f603c291.jpg?Expires=1482634687&OSSAccessKeyId=vGYn0xGpshJEzwyt&Signature=RULDLA7NIUCDQZRQuNXzcDWsTQU%3D";
//
//            Log.e(TAG, "onBindViewHolder: channelImage "+channelImage);
//            if (!channelImage.equals("")&&!channelImage.equals("null")) {
//                uri = Uri.parse(channelImage);
//            }else{
//            Uri  uri = Uri.parse("asset:///chat_default_group_image.png");
//            }
            String channel = String.valueOf(map.get("CHANNELID"));

            int unreadNum = 0;
//            String unReadMess = MyPreferenceManager.getString(Bitmap.Config.myName + "_unReadMess", "");
////如果已存在unReadMessMap
//            Log.e(TAG, "onBindViewHolder: Config.myName " + Bitmap.Config.myName);
//            Log.e(TAG, "onBindViewHolder: unReadMess " + unReadMess);
//            if (!unReadMess.equals("")) {
////unReadMessMap中存放频道和未读数
//                Map<String, Integer> unReadMessMap = JSON.parseObject(unReadMess,
//                        new TypeReference<Map<String, Integer>>() {
//                        });
////频道号和数目
//                //
//                if (unReadMessMap.get(channel) != null) {
//                    unreadNum = unReadMessMap.get(channel);
//                }
//
//            }
            Log.e(TAG, "onBindViewHolder: unreadNum " + unreadNum);
            //消息数为0时隐藏角标
            if (unreadNum == 0) {
                holder.tv_psn_msg.setVisibility(View.GONE);
            } else {
                holder.tv_psn_msg.setVisibility(View.VISIBLE);
            }
            BadgeView badgeView1 = new com.jauker.widget.BadgeView(mContext);
            badgeView1.setTargetView(holder.tv_psn_msg);
            badgeView1.setBadgeCount(unreadNum);


//会话对象名称
//            String channelName = String.valueOf(map.get(customChannelName));
//            String defaultName = String.valueOf(map.get(defaultChannelName));
//            holder.tv_title.setText(!channelName.equals("null") ? channelName : defaultName);
            Uri uri = Uri.parse("asset:///chat_default_group_image.png");
            if ((holder.tv_title.getText().toString()).contains("教学通知")) {
                uri = Uri.parse("asset:///session_teach_notice.png");
            } else if ((holder.tv_title.getText().toString()).contains("售后服务")) {
                uri = Uri.parse("asset:///session_after_sale_service.png");
            }
            holder.group_image.setImageURI(uri);

//最后一次的聊天内容，暂时取的全部聊天内容
//            String latestMsg = String.valueOf(map.get(chanelLatestMsg));
//            holder.tv_psn_name.setText(!latestMsg.equals("null") ? latestMsg : "");
////最后一次聊天的时间
//            String latestTime = String.valueOf(map.get(chanelLatestTime));
//            holder.tv_time.setText(!latestTime.equals("null") ? latestTime : "");
//设置tag
            holder.itemView.setTag(map);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, JSON.toJSONString(view.getTag()));
        }
    }

    // 可复用的VH
    class ComViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_psn_name, tv_time, tv_psn_msg;
        SimpleDraweeView group_image;
        LinearLayout session_layout_all;

        public ComViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_psn_msg = (TextView) itemView.findViewById(R.id.tv_psn_msg);
            tv_psn_name = (TextView) itemView.findViewById(R.id.tv_psn_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            group_image = (SimpleDraweeView) itemView.findViewById(R.id.group_image);
            session_layout_all = (LinearLayout) itemView.findViewById(R.id.session_layout_all);
        }
    }


    /**
     * 获取单项数据
     */

    private Map<String, Object> getData(int position) {

        return mDatas.get(position);
    }

    /**
     * 获取全部数据
     */
    public List<Map<String, Object>> getDatas() {

        return mDatas;
    }

    /**
     * 清除数据
     */
    public void clearData() {

        mDatas.clear();
        notifyItemRangeRemoved(0, mDatas.size());
    }

    /**
     * 下拉刷新更新数据
     */
    public void addData(List<Map<String, Object>> datas) {

        addData(0, datas);
    }

    /**
     * 上拉加载添加数据的方法
     */
    public void addData(int position, List<Map<String, Object>> datas) {
        if (datas != null && datas.size() > 0) {

            mDatas.addAll(datas);
            notifyItemRangeChanged(position, mDatas.size());
        }

    }
}

//角标示例
//private void initData() {
//    BadgeView badgeView1 = new com.jauker.widget.BadgeView(this);
//    badgeView1.setTargetView(mTipText1);
//    badgeView1.setBadgeCount(3);
//
//
//    BadgeView badgeView2 = new BadgeView(this);
//    badgeView2.setTargetView(mTipText2);
//    badgeView2.setBackground(12, Color.parseColor("#9b2eef"));
//    badgeView2.setText("提示");
//
//    BadgeView badgeView3 = new BadgeView(this);
//    badgeView3.setTargetView(mTipText3);
//    badgeView3.setBadgeGravity(Gravity.TOP | Gravity.LEFT);
//    badgeView3.setTypeface(Typeface.create(Typeface.SANS_SERIF,
//            Typeface.ITALIC));
//    badgeView3.setShadowLayer(2, -1, -1, Color.GREEN);
//    badgeView3.setBadgeCount(2);
//}