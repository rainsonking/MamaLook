package kehuhuaPage.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class EventNoticeBaseAdapter extends BaseAdapter {
    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;

    public EventNoticeBaseAdapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;
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

    private static final String TAG = "EventNoticeBaseAdapter";
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if (convertView == null) {
            //解析布局
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_event_notice_list_item, null);
            //创建ViewHolder持有类
            viewHolder = new ViewHolder();
            //将每个控件的对象保存到持有类中

            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            //将每个convertView对象中设置这个持有类对象
            convertView.setTag(viewHolder);
        } else
            //每次需要使用的时候都会拿到这个持有类
            viewHolder = (ViewHolder) convertView.getTag();
        Log.e(TAG, "getView: list " + list.size() + " " + position);

        Map<String, Object> map = list.get(position);
        viewHolder.tv_title.setText(map.get("title").toString());
        viewHolder.tv_date.setText(map.get("date").toString());

        return convertView;
    }

    class ViewHolder {
        public TextView tv_title, tv_date;
    }
}
