package kehuhuaPage.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class StudyLogBaseAdapter extends BaseAdapter {
    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;

    public StudyLogBaseAdapter(Context mContext, List<Map<String, Object>> mlist) {
        this.context = mContext;
        this.list = mlist;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static final String TAG = "StudyLogBaseAdapter";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            //解析布局
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_study_log_listitem, null);
            //创建ViewHolder持有类
            viewHolder = new ViewHolder();
            //将每个控件的对象保存到持有类中
            viewHolder.tv_left = (TextView) convertView.findViewById(R.id.tv_left);
            viewHolder.tv_center = (TextView) convertView.findViewById(R.id.tv_center);
            viewHolder.tv_right = (TextView) convertView.findViewById(R.id.tv_right);
            //将每个convertView对象中设置这个持有类对象
            convertView.setTag(viewHolder);
        } else
            //每次需要使用的时候都会拿到这个持有类
            viewHolder = (ViewHolder) convertView.getTag();
        Log.e(TAG, "getView: list "+list.size() +" "+position);

        Map<String, Object> map = list.get(position);
        String isLog = map.get("islog").toString();
        if (isLog.equals("1")) {
            viewHolder.tv_left.setVisibility(View.VISIBLE);
            viewHolder.tv_left.setText(map.get("content").toString());
            viewHolder.tv_right.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tv_right.setVisibility(View.VISIBLE);
            viewHolder.tv_right.setText(map.get("content").toString());
            viewHolder.tv_left.setVisibility(View.INVISIBLE);
        }
        viewHolder.tv_center.setText(map.get("date").toString());
        return convertView;
    }

    class ViewHolder {
        public TextView tv_left, tv_center, tv_right;
    }
}
