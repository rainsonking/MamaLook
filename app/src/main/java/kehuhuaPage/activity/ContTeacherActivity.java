package kehuhuaPage.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import kehuhuaPage.adpter.ListBaseAdapter;
import kehuhuaPage.widgts.CnToolbar;
import kehuhuaPage.widgts.CustomLinearLayoutManager;
import kehuhuaPage.widgts.MyReceiver;
import okhttp3.Call;
import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class ContTeacherActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
   private SwipeRefreshLayout mSwipeRefreshLayout;
    private int totalNum = 0;
    private int start = 0;
    private final int limit = 200;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;
    private CnToolbar mToolbar;
    private TextView unreadSysMsgNum;
    private List<Map<String, Object>> datas = new ArrayList<>();
    private ListBaseAdapter adapter;
    private SimpleDraweeView sysMessage;
    private LinearLayout sys_message_ll;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            refreshData();
        }
    };
    BroadcastReceiver broadcastReceiverChatGo = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            refreshData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont_teacher);
        initView();
//        dialog.show();
        initView();
        initRefreshLayout();
        IntentFilter filter = new IntentFilter(MyReceiver.actionRefreshSessionList);
        registerReceiver(broadcastReceiver, filter);
        IntentFilter filterChatGo = new IntentFilter(ChatGoActivity.actionRefreshSessionListChatGo);
        registerReceiver(broadcastReceiverChatGo, filterChatGo);
        requestData();
    }

    @Override
    public void initView() {
        mToolbar = (CnToolbar) findViewById(R.id.stu_toolbar);
        mToolbar.setTitle("聊天");
        mRecyclerView= (RecyclerView) findViewById(R.id.lv);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
//        mRecyclerView.addItemDecoration(new RecyclerViewDivider(
//                     getActivity(), LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(getActivity(), R.color.red)));

    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
//        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //设置卷内的颜色
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.red));
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.white);
        //设置下拉刷新监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }


    /**
     * 下拉刷新方法
     */
    private void refreshData() {
        start = 0;
        state = STATE_REFREH;
        requestData();
    }

    /**
     * 上拉加载方法
     */
    private void loadMoreData() {
        start += limit;
        state = STATE_MORE;
        requestData();
    }

    private static final String TAG = "ContTeacherActivity";
    /**
     * 分动作展示数据
     */
    private void showData() {
        Log.e(TAG, "showData: " + state);
        switch (state) {
            case STATE_NORMAL:
                normalRequest();
                break;
            case STATE_REFREH:
                if (adapter != null) {
                    adapter.clearData();
                    adapter.addData(datas);
                    adapter.notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(0);
                    mSwipeRefreshLayout.setRefreshing(false);//直接设置不刷新
//                    if (datas.size() > 0) {
//                        Snackbar.make(mRecyclerView, "共"+totalNum+"条", Snackbar.LENGTH_SHORT).show();
//                    }
                }

                break;
            case STATE_MORE:
                if (adapter != null) {
                    adapter.addData(adapter.getDatas().size(), datas);
                    mRecyclerView.scrollToPosition(adapter.getDatas().size());
                    mSwipeRefreshLayout.setRefreshing(false);
//                    Snackbar.make(mRecyclerView, "更新了" + childDatas.size() + "条数据", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void requestData() {
//        if (((BaseActivity) getActivity()).hasInternetConnected()) {
//            //地址
//            String volleyUrl = Constant.sysUrl + Constant.requestListSet;
//            Log.e("TAG", "请求会话列表数据 " + Constant.sysUrl + Constant.requestListSet);
//            //参数
//            Map<String, String> map = new HashMap<>();
//            map.put(Constant.tableId, channelObjTable);
//            map.put(Constant.pageId, channelListPageId);
//            map.put(Constant.start, start + "");
//            map.put(Constant.limit, limit + "");
//            map.put("sessionId", Constant.sessionId);
//            Log.e("TAG", "学员端登陆map " + map.toString());
//            //请求
//            OkHttpUtils
//                    .post()
//                    .params(map)
//                    .url(volleyUrl)
//                    .build()
//                    .execute(new EdusStringCallback1(getActivity()) {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            ErrorToast.errorToast(mContext, e);
//                            mSwipeRefreshLayout.setRefreshing(false);
//                            ((BaseActivity) getActivity()).dialog.dismiss();
//                            backStart();
//                        }
//
//                        @Override
//                        public void onResponse1(String response, int id) {
//                            Log.e("", "onResponse: " + "  id  " + response);
//                            if (!response.equals("")) {
//                                check(response);
//                            } else {
////                                ((BaseActivity) getActivity()).dialog.dismiss();
//                                mSwipeRefreshLayout.setRefreshing(false);
//                                Toast.makeText(getActivity(), "无会话数据", Toast.LENGTH_SHORT).show();
//                            }
//                            reqHeadPortUri(Constant.sysUrl + Constant.meiJiaAlDownLoadFileStr + Constant.F_MONGOID);
//                        }
//                    });
//        } else {
//            ((BaseActivity) getActivity()).dialog.dismiss();
//            mSwipeRefreshLayout.setRefreshing(false);
//            Toast.makeText(getActivity(), "请连接网络", Toast.LENGTH_SHORT).show();
//            backStart();
//        }
    }

    public void backStart() {

        //下拉失败后需要将加上limit的strat返还给原来的start，否则会获取不到数据
        if (state == STATE_MORE) {
            //start只能是limit的整数倍
            if (start > limit) {
                start -= limit;
            }
        }
    }

//    public void check(String menuData) {
//        try {
//            Map<String, Object> menuMap = JSON.parseObject(menuData,
//                    new TypeReference<Map<String, Object>>() {
//                    });
//            List<Map<String, Object>> menuListMap2 = null;
//            if (menuMap.containsKey("dataList")) {
//                menuListMap2 = (List<Map<String, Object>>) menuMap.get("dataList");
//                Log.e("menuListMap2", JSON.toJSONString(menuListMap2));
//            }
////        childDatas.clear();
//            totalNum = Integer.parseInt(menuMap.get("dataCount") + "");
//            Log.e("menuListMap2num", menuMap.get("dataCount") + "");
//            if (menuListMap2 != null && menuListMap2.size() > 0) {
//                datas = menuListMap2;
//            }
//            showData();
//        } catch (NumberFormatException e) {
//            PgyCrashManager.reportCaughtException(getActivity(), e);//蒲公英crash报告
//            e.printStackTrace();
//        }
//    }

    private boolean mIsRefreshing = false;

    private void normalRequest() {
        adapter = new ListBaseAdapter(datas, this);
        mRecyclerView.setAdapter(adapter);
        final CustomLinearLayoutManager customLinearLayoutManager;
        customLinearLayoutManager = new CustomLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(customLinearLayoutManager);
        adapter.setOnItemClickListener(new ListBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Log.e("TAG", "data " + data);
                // Toast.makeText(ConvListActivity.this, data, Toast.LENGTH_SHORT).show();
                toItem(data);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visible = customLinearLayoutManager.getChildCount();
                int total = customLinearLayoutManager.getItemCount();
                int past = customLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if ((visible + past) >= total) {
                    if (total < totalNum) {
                        loadMoreData();
                    }

                }
            }
        });
        //解决刷新中滑动崩溃问题
//        mRecyclerView.setOnTouchListener(
//                new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (mIsRefreshing) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }
//                }
//        );
//        ((BaseActivity) getActivity()).dialog.dismiss();
    }

    private void toItem(String data) {
        Map<String, Object> menuMap = JSON.parseObject(data,
                new TypeReference<Map<String, Object>>() {
                });
//        Log.e(TAG, "onItemClick: CHANNELID" + menuMap.get("CHANNELID").toString());
//        Log.e(TAG, "onItemClick: CHANNELNAME" + menuMap.get("CHANNELNAME").toString());
        Intent intent = new Intent(this, ChatGoActivity.class);
        intent.putExtra("dataId", String.valueOf(menuMap.get("CHANNELID")));
//        String channelName = String.valueOf(menuMap.get(customChannelName));
//        String defaultName = String.valueOf(menuMap.get(defaultChannelName));
//        intent.putExtra("channelName", channelName.equals("null") ? defaultName : channelName);
        Log.e(TAG, "toItem: CHANNELID " + String.valueOf(menuMap.get("CHANNELID")));
        Log.e(TAG, "toItem: CHANNELNAME " + String.valueOf(menuMap.get("CHANNELNAME")));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        //返回sessionFragment后应该将当前频道置空
//        currentChannel = "";
//        currentChannelName = "";
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        unregisterReceiver(broadcastReceiver);
    }


    private void reqHeadPortUri(final String url) {
        if (hasInternetConnected()) {
//            //请求
//            OkHttpUtils
//                    .post()
//                    .url(url)
//                    .build().execute(new EdusStringCallback1(getActivity()) {
//                @Override
//                public void onError(Call call, Exception e, int id) {
//                    ErrorToast.errorToast(getActivity(), e);
//                    ((BaseActivity) getActivity()).dialog.dismiss();
//                }
//
//                @Override
//                public void onResponse1(String response, int id) {
//                    Log.e(TAG, "onResponse1: " + response);
//                    Uri uri = Uri.parse(response);
//                    Constant.chatGoPerImgUri = uri;
//                    ((BaseActivity) getActivity()).dialog.dismiss();
//                }
//            });
        } else {
            try {
                Looper.prepare();
                Toast.makeText(this, "无网络！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
}
