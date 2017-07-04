package kehuhuaPage.adpter;

import android.content.Context;
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
 * Created by Administrator on 2017/7/3 0003.
 */

public class HomeWorkBaseAdapter extends BaseAdapter {
    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;

    public HomeWorkBaseAdapter(Context mContext, List<Map<String, Object>> mList) {
        this.context = mContext;
        this.list = mList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            //解析布局
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_homework_item, null);
            //创建ViewHolder持有类
            viewHolder = new ViewHolder();
            //将每个控件的对象保存到持有类中
            viewHolder.iv_unit_count = (TextView) convertView.findViewById(R.id.iv_unit_count);
            viewHolder.iv_img1 = (ImageView) convertView.findViewById(R.id.iv_img1);
            viewHolder.iv_img2 = (ImageView) convertView.findViewById(R.id.iv_img2);
            viewHolder.iv_img3 = (ImageView) convertView.findViewById(R.id.iv_img3);
            viewHolder.iv_img4 = (ImageView) convertView.findViewById(R.id.iv_img4);
            viewHolder.iv_img5 = (ImageView) convertView.findViewById(R.id.iv_img5);
            viewHolder.iv_img6 = (ImageView) convertView.findViewById(R.id.iv_img6);
            viewHolder.iv_img7 = (ImageView) convertView.findViewById(R.id.iv_img7);
            viewHolder.iv_img8 = (ImageView) convertView.findViewById(R.id.iv_img8);

            //将每个convertView对象中设置这个持有类对象
            convertView.setTag(viewHolder);
        } else
            //每次需要使用的时候都会拿到这个持有类
            viewHolder = (ViewHolder) convertView.getTag();

        Map<String, Object> map = list.get(position);
        //然后可以直接使用这个类中的控件，对控件进行操作，而不用重复去findViewById了
        viewHolder.iv_unit_count.setText(map.get("unit").toString());
//        viewHolder.iv_img1.setBackgroundResource(R.mipmap.ic_launcher);
        return convertView;
    }

    class ViewHolder {
        public TextView iv_unit_count;
        public ImageView iv_img1;
        public ImageView iv_img2;
        public ImageView iv_img3;
        public ImageView iv_img4;

        public ImageView iv_img5;
        public ImageView iv_img6;
        public ImageView iv_img7;
        public ImageView iv_img8;
        public ImageView iv_light;
    }
}
