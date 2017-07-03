package kehuhuaPage.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kehuhuaPage.widgts.ListViews;
import kehuhuaPage.widgts.VpSwipeRefreshLayout;
import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/3 0003.
 *
 */

public class HomeWorkActivity extends BaseActivity  implements View.OnClickListener {
    private static final String TAG = "HomeWorkActivity";
    private PopupWindow stageNameWindow;//下拉pop
    private ListViews lvHomework;//电池列表
    private ImageView iv_on_off;
    private TextView tv_title;//LEVEL名称
    private LinearLayout layout_stage_name;//LEVEL名称和箭头合并的layout，点击下拉的按钮
    private String[] stageNames;//LEVEL数组
    private List<Map<String, Object>> dataList = new ArrayList<>();//下拉按钮数据存放
    private VpSwipeRefreshLayout pull_refresh_scrollview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        initView();
        requestSet();
    }

    @Override
    public void initView() {
        LinearLayout layout_title_left_third = (LinearLayout) findViewById(R.id.layout_title_left_third);
        iv_on_off = (ImageView) findViewById(R.id.iv_on_off);
        iv_on_off.setBackgroundResource(R.mipmap.click_down);
        tv_title = (TextView) findViewById(R.id.tv_title);
        layout_stage_name = (LinearLayout) findViewById(R.id.layout_stage_name);
        layout_stage_name.setOnClickListener(this);
        layout_title_left_third.setOnClickListener(this);
        lvHomework = (ListViews) findViewById(R.id.lv_homework);


        pull_refresh_scrollview = (VpSwipeRefreshLayout) findViewById(R.id.pull_refresh_scrollview);
        //设置卷内的颜色
        pull_refresh_scrollview.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimary));
        pull_refresh_scrollview.setColorSchemeResources(android.R.color.white);
        pull_refresh_scrollview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestSet();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_stage_name:
                initWindow();
                break;
            case R.id.layout_title_left_third:
                finish();
                break;
        }
    }


    private void initWindow() {
        if (stageNameWindow != null && stageNameWindow.isShowing()) {
            stageNameWindow.dismiss();
        } else {
            View view = getLayoutInflater().inflate(R.layout.window_stage_name, null);
            ListView lv_stage_name = (ListView) view.findViewById(R.id.lv_stage_name);

            if (dataList.size() > 0) {
                stageNames = new String[dataList.size()];
                for (int i = 0; i < dataList.size(); i++) {
                    stageNames[i] = String.valueOf(dataList.get(i).get("PT_NAME"));
                }
                ArrayAdapter adapter = new ArrayAdapter<>(HomeWorkActivity.this, R.layout.stage_name_item, R.id.tv_stage_name, stageNames);
                lv_stage_name.setAdapter(adapter);

            } else {
                return;
            }

            lv_stage_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    resetData(dataList.get(position));
                    tv_title.setText(stageNames != null ? stageNames[position] : "");
                    stageNameWindow.dismiss();
                }
            });

            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels;     // 屏幕宽度（像素）
            stageNameWindow = new PopupWindow(view, (width / 3) * 2,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            iv_on_off.setBackgroundResource(R.mipmap.click_up);
            stageNameWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    iv_on_off.setBackgroundResource(R.mipmap.click_down);
                }
            });

            ColorDrawable cd = new ColorDrawable(0b1);
            stageNameWindow.setBackgroundDrawable(cd);
            stageNameWindow.setAnimationStyle(R.style.PTPopupWindowAnimation);
            stageNameWindow.setOutsideTouchable(true); // 设置popupwindow外部可点击
            stageNameWindow.setFocusable(true); // 获取焦点
            stageNameWindow.showAsDropDown(layout_stage_name, layout_stage_name.getWidth() / 2 - stageNameWindow.getWidth() / 2, 0);
            stageNameWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        stageNameWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }
    }



    public void requestSet() {

        List<Map<String, Object>> aa=new ArrayList<>();
        Map<String, Object> aaMap1=new HashMap<>();
        aaMap1.put("PT_NAME","LEVEL 1");

        Map<String, Object> aaMap2=new HashMap<>();
        aaMap2.put("PT_NAME","LEVEL 2");

        Map<String, Object> aaMap3=new HashMap<>();
        aaMap3.put("PT_NAME","LEVEL 3");
        aa.add(aaMap1);
        aa.add(aaMap2);
        aa.add(aaMap3);
        dataList = aa;
        resetData(dataList.get(0));
        tv_title.setText(String.valueOf(dataList.get(0).get("PT_NAME")));

    }

    private void resetData(Map<String, Object> stringObjectMap) {
        Log.e(TAG, "onClick: stringObjectMap " + stringObjectMap.toString());
        final Map<String, Object> itemSet = new HashMap<>();
        //根据选择的map中的参数，请求对应的数据后填充listView

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pull_refresh_scrollview.setRefreshing(false);
    }
}
