package kehuhuaPage.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import kehuhuaPage.widgts.VpSwipeRefreshLayout;
import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/3 0003.
 *
 */

public class StudyLogActivity extends BaseActivity {
    private VpSwipeRefreshLayout pull_refresh_scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_log);
        initView();

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
    }
}
