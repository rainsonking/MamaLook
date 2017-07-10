package kehuhuaPage.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kehuhuaPage.adpter.StudyLogBaseAdapter;
import kehuhuaPage.widgts.ListViews;
import kehuhuaPage.widgts.VpSwipeRefreshLayout;
import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class StudyLogActivity extends BaseActivity {
    private VpSwipeRefreshLayout pull_refresh_scrollview;
    private ListViews lv_listview;
    private List<Map<String, Object>> list = new ArrayList<>();
    private StudyLogBaseAdapter adapter;
    private static final String TAG = "StudyLogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_log);
        initView();
        initData();
    }

    private void initData() {
        Map<String, Object> map = new HashMap<String, Object>();
        //1 左边，2 右边
        map.put("islog", 1);
        map.put("date", "4月29日");
        map.put("content", "第三届拼词大赛三等奖");
        list.add(map);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("islog", 2);
        map1.put("date", "5月29日");
        map1.put("content", "第三届拼词大赛三等奖");
        list.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("islog", 2);
        map2.put("date", "6月29日");
        map2.put("content", "第三届拼词大赛三等奖");
        list.add(map2);
        Log.e(TAG, "initData: list " + list.size());
        adapter = new StudyLogBaseAdapter(StudyLogActivity.this, list);
        lv_listview.setAdapter(adapter);
    }


    @Override
    public void initView() {
        pull_refresh_scrollview = (VpSwipeRefreshLayout) findViewById(R.id.pull_refresh_scrollview);
        //设置卷内的颜色
        pull_refresh_scrollview.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimary));
        pull_refresh_scrollview.setColorSchemeResources(android.R.color.white);
        pull_refresh_scrollview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pull_refresh_scrollview.setRefreshing(false);
            }
        });

        lv_listview = (ListViews) findViewById(R.id.lv_listview);

    }
}
