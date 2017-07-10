package kehuhuaPage.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kehuhuaPage.adpter.EventNoticeBaseAdapter;
import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class EventNoticeActivity extends BaseActivity {
    private ListView mListView;
    EventNoticeBaseAdapter adapter;
    private List<Map<String, Object>> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_notice);

        initView();
        initData();
    }

    @Override
    public void initView() {
        mListView = (ListView) findViewById(R.id.lv_listview);
    }

    private void initData() {
        adapter = new EventNoticeBaseAdapter(mlist,this);
        Map<String, Object> map = new HashMap<>();
        map.put("title", "2017端午节优惠活动详情");
        map.put("date", "2017年2月1日");
        mlist.add(map);
        map = new HashMap<>();
        map.put("title", "2017端午节优惠活动详情");
        map.put("date", "2017年2月1日");
        mlist.add(map);
        mListView.setAdapter(adapter);
    }
}
