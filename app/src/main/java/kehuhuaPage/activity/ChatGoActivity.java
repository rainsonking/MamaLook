package kehuhuaPage.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.weixindemo.CameraActivity;
import com.example.weixindemo.ChatMsgEntity;
import com.example.weixindemo.FaceRelativeLayout;
import com.example.weixindemo.ScaleImageFromSdcardActivity;
import com.example.weixindemo.SoundMeter;
import com.example.weixindemo.view.AutoTopLoadMoreListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kehuhuaPage.adpter.ChatMsgViewAdapter;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import okhttp3.Call;
import razerdp.friendcircle.R;

import static java.lang.String.valueOf;


public class ChatGoActivity extends BaseActivity implements OnClickListener {
    private LinearLayout mBtnSet;
    LinearLayout mBtnBack;
    private Button mBtnSend;
    private EditText mEditTextContent;
    //    private XCPullToLoadMoreListView mListView;
    private RelativeLayout mBottom, rl_layout;
    private TextView mBtnRcd, chat_title;
    private ChatMsgViewAdapter mAdapter;
    private List<ChatMsgEntity> mDateArrays;

    //语音
    private ImageView chatting_mode_btn, volume;
    private boolean btn_voice = false;
    private LinearLayout del_re;
    private int flag = 1;
    private LinearLayout voice_rcd_hint_loading, voice_rcd_hint_rcding,
            voice_rcd_hint_tooshort;
    private View rcChat_popup;
    private Handler mHandler = new Handler();
    private boolean isShosrt = false;
    private ImageView img1, sc_img1, btn_photo;
    private String voiceName;
    private long startVoiceT, endVoiceT;
    private SoundMeter mSensor;
    //private ImageButton btn_face;

    public static final int SHOW_ALL_PICTURE = 0x14;//查看图片
    public static final int SHOW_PICTURE_RESULT = 0x15;//查看图片返回
    public static final int CLOSE_INPUT = 0x01;//关闭软键盘
    public static Handler handlerInput;//用于软键盘+
    //private String photoName;
    public ImageView iv_sec, iv_third, iv_four, iv_fifth;

    //调用系统相册-选择图片
    private static final int IMAGE = 90;
    private ArrayList<String> imgPaths = new ArrayList<>();
    private ListView mInnerListView;
    private AutoTopLoadMoreListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.weixindemo.R.layout.chat);
//        MyApplication.getInstance().addActivity(this);
        //设置启动不弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				FaceConversionUtil.getInstace().getFileText(getApplication());
//			}
//		}).start();
        dialog.show();
        initView();
        getSessionPragramData();
        clearChannelUnreadData();


    }

    public static final String actionRefreshSessionListChatGo = "com.kwsoft.kehuhua.fragments.sessionFragment.CHAT_GO_REFRESH_LIST";

    private void clearChannelUnreadData() {
//        String unReadMess = MyPreferenceManager.getString(Config.myName + "_unReadMess", "");
//        if (!unReadMess.equals("")) {
////unReadMessMap中存放频道和未读数
//            Map<String, Integer> unReadMessMap = JSON.parseObject(unReadMess,
//                    new TypeReference<Map<String, Integer>>() {
//                    });
/////删除该频道下所有未读消息
//            unReadMessMap.remove(dataId);
//            MyPreferenceManager.commitString(Config.myName + "_unReadMess", JSON.toJSONString(unReadMessMap));
//            Intent intent = new Intent(actionRefreshSessionListChatGo);
//            sendBroadcast(intent);
//            Log.e(TAG, "unreadMessage: 已增加");
//        }

    }

    public void initView() {
        //btn_face = (ImageButton)findViewById(R.id.btn_face);
        chat_title = (TextView) findViewById(com.example.weixindemo.R.id.chat_title);
        btn_photo = (ImageView) findViewById(com.example.weixindemo.R.id.btn_photo);
        //btn_photo = (ImageView)findViewById(com.example.weixindemo.R.id.btn_photo);
        mListView = (AutoTopLoadMoreListView) findViewById(com.example.weixindemo.R.id.listview1);
        mBtnSend = (Button) findViewById(com.example.weixindemo.R.id.btn_send);
        mBtnBack = (LinearLayout) findViewById(com.example.weixindemo.R.id.ll_back);
        mBtnSet = (LinearLayout) findViewById(com.example.weixindemo.R.id.group_set);
        rl_layout = (RelativeLayout) findViewById(com.example.weixindemo.R.id.rl_layout);
        rl_layout.setBackgroundColor(getResources().getColor(R.color.red));
        chatting_mode_btn = (ImageView) findViewById(com.example.weixindemo.R.id.ivPopUp);
        mEditTextContent = (EditText) findViewById(com.example.weixindemo.R.id.et_sendmessage);
        mBtnRcd = (TextView) findViewById(com.example.weixindemo.R.id.btn_rcd);
        mBottom = (RelativeLayout) findViewById(com.example.weixindemo.R.id.btn_bottom);
        del_re = (LinearLayout) this.findViewById(com.example.weixindemo.R.id.del_re);
        volume = (ImageView) this.findViewById(com.example.weixindemo.R.id.volume);
        img1 = (ImageView) this.findViewById(com.example.weixindemo.R.id.img1);
        sc_img1 = (ImageView) this.findViewById(com.example.weixindemo.R.id.sc_img1);
        rcChat_popup = this.findViewById(com.example.weixindemo.R.id.rcChat_popup);
        voice_rcd_hint_rcding = (LinearLayout) this
                .findViewById(com.example.weixindemo.R.id.voice_rcd_hint_rcding);
        voice_rcd_hint_loading = (LinearLayout) this
                .findViewById(com.example.weixindemo.R.id.voice_rcd_hint_loading);
        voice_rcd_hint_tooshort = (LinearLayout) this
                .findViewById(com.example.weixindemo.R.id.voice_rcd_hint_tooshort);
        mSensor = new SoundMeter();
        mBtnBack.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
        mBtnSet.setOnClickListener(this);
        //btn_photo.setOnClickListener(this);
        mEditTextContent.setOnClickListener(this);
        //btn_face.setOnClickListener(this);
        iv_sec = (ImageView) findViewById(com.example.weixindemo.R.id.iv_sec);
        iv_sec.setOnClickListener(this);

        iv_third = (ImageView) findViewById(com.example.weixindemo.R.id.iv_third);
        iv_third.setOnClickListener(this);

        chatting_mode_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_voice) {
                    mBtnRcd.setVisibility(View.GONE);
                    mBottom.setVisibility(View.VISIBLE);
                    btn_voice = false;
                    chatting_mode_btn.setImageResource(com.example.weixindemo.R.drawable.first);
                    ((FaceRelativeLayout) findViewById(com.example.weixindemo.R.id.FaceRelativeLayout)).hideFaceView();

                } else {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(ChatGoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mBtnRcd.setVisibility(View.VISIBLE);
                    mBottom.setVisibility(View.GONE);
                    chatting_mode_btn.setImageResource(com.example.weixindemo.R.drawable.first);
                    btn_voice = true;
                    ((FaceRelativeLayout) findViewById(com.example.weixindemo.R.id.FaceRelativeLayout)).hideFaceView();
                }
            }
        });
        mBtnRcd.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mEditTextContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //String text = mEditTextContent.getText().toString();
//				if(!(text.length()>0)){
//					//Toast.makeText(ChatGoActivity.this,mEditTextContent.getText()+"请输入聊天内容", 2).show();
//					btn_photo.setVisibility(View.VISIBLE);
//					//mBtnSend.setVisibility(View.GONE);
//				}else{
//					//Toast.makeText(ChatGoActivity.this,"输入的聊天内容为："+mEditTextContent.getText(), 2).show();
//					btn_photo.setVisibility(View.GONE);
//					//mBtnSend.setVisibility(View.VISIBLE);
//				}
            }

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {
            }
        });
//
//        mListView.setOnRefreshListener(new XCPullToLoadMoreListView.OnRefreshListener() {
//            @Override
//            public void onPullDownLoadMore() {
//                Log.v("czm", "onRefreshing");
//                addData();
//                mListView.onRefreshComplete();
//            }
//        });
    }


    private void addData() {


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            //判断坐标
            int leftInt = mListView.getLeft();
            int rightInt = mListView.getRight();
            int bottomInt = mListView.getBottom();
            int topInt = mListView.getTop();
            boolean isInListView = ev.getX() >= leftInt && ev.getX() <=
                    rightInt && ev.getY() <= bottomInt && ev.getY() >= topInt;
            if (isShouldHideInput(v, ev) && isInListView) {
                hideSoftInput(v.getWindowToken());
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }


    }

    //按下语音录制
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!Environment.getExternalStorageDirectory().exists()) {
            Toast.makeText(this, "无内存卡，请安装..", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (btn_voice) {
            System.out.println("1");
            int[] location = new int[2];
            mBtnRcd.getLocationInWindow(location);
            int btn_rc_Y = location[1];
            int btn_rc_X = location[0];
            int[] del_location = new int[2];
            del_re.getLocationInWindow(del_location);
            int del_Y = del_location[1];
            int del_x = del_location[0];
            if (event.getAction() == MotionEvent.ACTION_DOWN && flag == 1) {
                if (!Environment.getExternalStorageDirectory().exists()) {
                    Toast.makeText(this, "无内存卡，请安装..", Toast.LENGTH_SHORT).show();
                    return false;
                }
                System.out.println("2");
                if (event.getY() > btn_rc_Y && event.getX() > btn_rc_X) {//判断手势按下的位置是否是语音录制按钮的范围内
                    System.out.println("3");
                    mBtnRcd.setBackgroundResource(com.example.weixindemo.R.drawable.voice_rcd_btn_pressed);
                    rcChat_popup.setVisibility(View.VISIBLE);
                    voice_rcd_hint_loading.setVisibility(View.VISIBLE);
                    voice_rcd_hint_rcding.setVisibility(View.GONE);
                    voice_rcd_hint_tooshort.setVisibility(View.GONE);
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (!isShosrt) {
                                voice_rcd_hint_loading.setVisibility(View.GONE);
                                voice_rcd_hint_rcding
                                        .setVisibility(View.VISIBLE);
                            }
                        }
                    }, 300);
                    img1.setVisibility(View.VISIBLE);
                    del_re.setVisibility(View.GONE);
                    startVoiceT = SystemClock.currentThreadTimeMillis();
                    voiceName = startVoiceT + ".amr";
                    start(voiceName);
                    flag = 2;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP && flag == 2) {
                System.out.println("4");
                mBtnRcd.setBackgroundResource(com.example.weixindemo.R.drawable.voice_rcd_btn_nor);
                if (event.getY() >= del_Y
                        && event.getY() <= del_Y + del_re.getHeight()
                        && event.getX() >= del_x
                        && event.getX() <= del_x + del_re.getWidth()) {
                    rcChat_popup.setVisibility(View.GONE);
                    img1.setVisibility(View.VISIBLE);
                    del_re.setVisibility(View.GONE);
                    stop();
                    flag = 1;
                    File file = new File(Environment.getExternalStorageDirectory() + "/"
                            + voiceName);
                    if (file.exists()) {
                        file.delete();
                    }
                } else {
                    voice_rcd_hint_rcding.setVisibility(View.GONE);
                    stop();
                    endVoiceT = SystemClock.currentThreadTimeMillis();
                    flag = 1;
                    int time = (int) ((endVoiceT - startVoiceT) / 50);
                    if (time < 1) {
                        isShosrt = true;
                        voice_rcd_hint_loading.setVisibility(View.GONE);
                        voice_rcd_hint_rcding.setVisibility(View.GONE);
                        voice_rcd_hint_tooshort.setVisibility(View.VISIBLE);
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                voice_rcd_hint_tooshort
                                        .setVisibility(View.GONE);
                                rcChat_popup.setVisibility(View.GONE);
                                isShosrt = false;
                            }
                        }, 500);
                        return false;
                    }
                    ChatMsgEntity entity = new ChatMsgEntity();
                    entity.setDate(getDate());
                    entity.setName("消息");
                    entity.setMsgType(false);
                    entity.setTime(time + "\"");
                    entity.setText(voiceName);
                    mDateArrays.add(entity);
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(mListView.getCount() - 1);
                    rcChat_popup.setVisibility(View.GONE);
                    //在此请求服务器
                    File file = new File(Environment.getExternalStorageDirectory() + "/"
                            + voiceName);
                    uploadMethod(file);
                }
            }
            if (event.getY() < btn_rc_Y) {//手势按下的位置不在语音录制按钮的范围内
                System.out.println("5");
                Animation mLitteAnimation = AnimationUtils.loadAnimation(this,
                        com.example.weixindemo.R.anim.cancel_rc);
                Animation mBigAnimation = AnimationUtils.loadAnimation(this,
                        com.example.weixindemo.R.anim.cancel_rc2);
                img1.setVisibility(View.GONE);
                del_re.setVisibility(View.VISIBLE);
                del_re.setBackgroundResource(com.example.weixindemo.R.drawable.voice_rcd_cancel_bg);
                if (event.getY() >= del_Y
                        && event.getY() <= del_Y + del_re.getHeight()
                        && event.getX() >= del_x
                        && event.getX() <= del_x + del_re.getWidth()) {
                    del_re.setBackgroundResource(com.example.weixindemo.R.drawable.voice_rcd_cancel_bg_focused);
                    sc_img1.startAnimation(mLitteAnimation);
                    sc_img1.startAnimation(mBigAnimation);
                }
            } else {

                img1.setVisibility(View.VISIBLE);
                del_re.setVisibility(View.GONE);
                del_re.setBackgroundResource(0);
            }
        }
        return super.onTouchEvent(event);
    }


    //解析文件上传成功的code值
    private String getFileCode(String response) {
        String[] valueTemp1 = response.split(":");
        Log.e(TAG, "getFileCode: "+valueOf(valueTemp1[1]));
        return valueOf(valueTemp1[1]);

    }

    //发送消息
    public void sendFile(String messType, String fileCode) {

//        if (!fileCode.equals("") && !fileCode.equals("null")) {
//            String volleyUrl = Constant.sysUrl + Constant.commitAdd;
//            Log.e("TAG", "发送消息接口地址 " + Constant.sysUrl + Constant.commitAdd);
//            //参数
//            Map<String, String> map = new HashMap<>();
//            map.put(Constant.tableId, chatObjTable);
//            map.put(Constant.pageId, sendMessPageId);
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36729", dataId);
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36737", messType);//文字类型消息发送参数
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36731", "");
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36752", "");
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36751", fileCode);
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36740", configRole);//发送者类型教师角色值1703  学员角色值1702
//            //附加参数
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36739", chatObjTable);
//            map.put("strFlag", "appMsg");
//            map.put("userType", StuPra.aliasTitle);//教师user,学员stu
//            map.put("sendType", "4");
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36974", Constant.loginName);
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36975", Constant.USERID);
//            map.put("sessionId", Constant.sessionId);
//
//            Log.e(TAG, "发送消息参数  " + map.toString());
//            //请求
//            OkHttpUtils
//                    .post()
//                    .url(volleyUrl)
//                    .params(map)
//                    .build()
//                    .execute(new EdusStringCallback1(ChatGoActivity.this) {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            ErrorToast.errorToast(mContext, e);
//                            //        dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onResponse1(String response, int id) {
//                            Log.e("ChatActivity", "onResponse: " + "  id  " + response);
//                            // check(response);
//                            if (response != null && response.length() > 0) {
//                                ExampleUtil.showToast("附件发送成功！", getApplicationContext());
////                                mEditTextContent.setText("");//发送成功后设置text为空
////								etContent.setText("");
////								commitData();
//                            }
//                        }
//                    });
//        } else {
//            ExampleUtil.showToast("语音发送失败！", getApplicationContext());
//        }
//
//			ChatMsgEntity entity = new ChatMsgEntity();
//			entity.setDate(getDate());
//			entity.setName("古月哥欠");
//			entity.setMsgType(false);
//			entity.setText(conString);
//			mDateArrays.add(entity);
//			mAdapter.notifyDataSetChanged();
//			mListView.setSelection(mListView.getCount()-1);

    }


    private void start(String name) {
        mSensor.start(name);
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        try {
            mSensor.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        volume.setImageResource(com.example.weixindemo.R.drawable.amp1);
    }

    private static final int POLL_INTERVAL = 300;

    private Runnable mSleepTask = new Runnable() {
        public void run() {
            stop();
        }
    };
    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            updateDisplay(amp);
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);

        }
    };

    private String dataId = "";//即为会话对象Id
    private String title = "";//即为会话名称

    public void getSessionPragramData() {
//        dataId = getIntent().getStringExtra("dataId");
//        title = getIntent().getStringExtra("channelName");
//        chat_title.setText(title);
        Log.e(TAG, "getSessionPragramData: 聊天界面收到的dataId " + dataId);
        Log.e(TAG, "getSessionPragramData: 聊天界面收到的title " + title);
//        MyPreferenceManager.commitString(StuPra.PREF_CURRENT_CHATTING, dataId);
//        currentChannel = dataId;
//        currentChannelName = title;
        getData();

    }


//    private void getCount() {
//        String volleyUrl = Constant.sysUrl + Constant.requestListSet;
//        Log.e("TAG", "开始获取聊天界面数据，这是地址 " + Constant.sysUrl + Constant.requestListSet);
//
//        //参数
//        Map<String, String> map = new HashMap<>();
//        map.put(tableId, chatObjTable);
//        map.put(pageId, chatlListPageId);
//        map.put(Constant.mainTableId, channelObjTable);
//        map.put(Constant.mainPageId, channelListPageId);
//        map.put(Constant.mainId, dataId);
//        map.put(Constant.start, start + "");
//        map.put(Constant.limit, "0");
//        Log.e(TAG, "getData: 获取聊天信息的参数" + map.toString());
//
//        //请求
//        OkHttpUtils
//                .post()
//                .url(volleyUrl)
//                .params(map)
//                .build()
//                .execute(new EdusStringCallback(ChatGoActivity.this) {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        ErrorToast.errorToast(mContext, e);
////						mRefreshLayout.finishRefresh();
//                      dialog.dismiss();
////						backStart();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e(TAG, "onResponse: " + "  id  " + response);
//                        checkCount(response);//解析聊天数据
//                    }
//                });
//
//
//    }

//    private void checkCount(String response) {
//        Map<String, Object> menuMap = JSON.parseObject(response,
//                new TypeReference<Map<String, Object>>() {
//                });
//        if (menuMap != null && menuMap.containsKey("dataCount")) {
//            String dataCountStr = String.valueOf(menuMap.get("dataCount"));
//            Log.e(TAG, "checkCount: dataCountStr" + "" + "聊天消息总数 " + dataCountStr);
//            if (Utils.stringIsInteger(dataCountStr)) {
//                dataCount = Integer.valueOf(dataCountStr);
//                int yushu = dataCount % limit;
//                Log.e(TAG, "checkCount: yushu " + yushu);
////                if (yushu>20) {
////                    start= dataCount-yushu+1;
////                    Log.e(TAG, "checkCount: start1 "+start);
////                }else{
//                if (yushu == 0) {
//                    start = dataCount - limit + 1;
//                } else {
//                    start = dataCount - yushu + 1;
//                }
//                Log.e(TAG, "checkCount: start2 " + start);
////                }
//                getData();
//            }
//        }
//    }


    private int start = 0;
    private final int limit = 20;
//    private int dataCount = 0;

    private void getData() {
//        if (hasInternetConnected()) {
//            String volleyUrl = Constant.sysUrl + Constant.requestListSet;
//            Log.e("TAG", "开始获取聊天界面数据，这是地址 " + Constant.sysUrl + Constant.requestListSet);
//
//            //参数
//            Map<String, String> map = new HashMap<>();
//            map.put(tableId, chatObjTable);
//            map.put(pageId, chatlListPageId);
//            map.put(Constant.mainTableId, channelObjTable);
//            map.put(Constant.mainPageId, channelListPageId);
//            map.put(Constant.mainId, dataId);
//            map.put(Constant.start, start + "");
//            map.put(Constant.limit, limit + "");
//            map.put("sessionId", Constant.sessionId);
//            Log.e(TAG, "getData: 获取聊天信息的参数" + map.toString());
//
//            //请求
//            OkHttpUtils
//                    .post()
//                    .url(volleyUrl)
//                    .params(map)
//                    .build()
//                    .execute(new EdusStringCallback1(ChatGoActivity.this) {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            ErrorToast.errorToast(mContext, e);
////						mRefreshLayout.finishRefresh();
//                            dialog.dismiss();
////						backStart();
//                        }
//
//                        @Override
//                        public void onResponse1(String response, int id) {
//                            Log.e(TAG, "onResponse: " + "  id  " + response);
//                            check(response);//解析聊天数据
//                             }
//                    });
//        } else {
//            try {
//                Looper.prepare();
//                Toast.makeText(ChatGoActivity.this, "无网络！", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    private static final String TAG = "ChatGoActivity";
    public int megNum = 0;

    //解析聊天数据，使其转换至mDateArrays
    public void check(String menuData) {
        dialog.dismiss();
        Map<String, Object> menuMap = JSON.parseObject(menuData,
                new TypeReference<Map<String, Object>>() {
                });
        List<Map<String, Object>> menuListMap2;
        if (menuMap.get("dataCount") != null) {
            megNum = Integer.valueOf(String.valueOf(menuMap.get("dataCount")));
        }

        if (menuMap.containsKey("dataList")) {
            menuListMap2 = (List<Map<String, Object>>) menuMap.get("dataList");
            Log.e(TAG, "check: 聊天消息网络获取后的消息总数 " + menuMap.get("dataCount") + "");


            initData(menuListMap2);
        }
    }

    public void initData(List<Map<String, Object>> jsonData) {
        List<ChatMsgEntity> mDateArray = new ArrayList<>();
        for (int i = 0; i < jsonData.size(); i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
//            entity.setDate(valueOf(jsonData.get(i).get(StuPra.messageSendDate)));//获取并设置消息发送时间
//            String wantedUserId = valueOf(jsonData.get(i).get(StuPra.messageUserId));//获取userId用来判断是不是自己发的消息
            entity.setTable(valueOf(jsonData.get(i).get("CHUANTOUTABLE")));
            entity.setPage(valueOf(jsonData.get(i).get("CHUANTOUPAGE")));
            entity.setMain(valueOf(jsonData.get(i).get("CHUANTOUDATAID")));
            entity.setCttype(valueOf(jsonData.get(i).get("CTTYPE")));
//            String listName = valueOf(jsonData.get(i).get(StuPra.messageFromName));
//            if (wantedUserId.equals(Constant.USERID) && valueOf(jsonData.get(i).get(StuPra.messageSendType)).equals(StuPra.configRole)) {
//                entity.setName(StuPra.messageFromMe);//直接设置自己的名称
//                entity.setMsgType(false);//false为自己
//            } else {
//                entity.setName((listName.equals("null") || listName.equals("0")) ? StuPra.messageFromSys : listName);//获取并设置对方名称
//                entity.setMsgType(true);//true为其他人
//            }
//            entity.setText(valueOf(jsonData.get(i).get(StuPra.messageContent)));//获取并设置消息内容
            mDateArray.add(entity);
        }

        Collections.reverse(mDateArray);

        mDateArrays = mDateArray;

        showData();


    }

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private int state = STATE_NORMAL;

    /**
     * 下拉刷新方法
     */
    private void loadMoreData() {
        mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
        start += limit;
        state = STATE_REFREH;
        getData();

    }

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
                if (mAdapter != null) {
//                    Log.e(TAG, "showData: mDateArrays.size()1 "+mDateArrays.size());
//                    Log.e(TAG, "showData: mAdapter.getCount()1 "+mAdapter.getCount());
                    mAdapter.addData(0, mDateArrays);
//                    Log.e(TAG, "showData: mDateArrays.size()2 "+mDateArrays.size());
//                    Log.e(TAG, "showData: mAdapter.getCount()2 "+mAdapter.getCount());
//                    mListView.setPos(mDateArrays.size()-1);

                    int i = mDateArrays.size();
                    mListView.requestFocus();
                    mListView.setItemChecked(i, true);
                    mListView.setSelection(i);
//                    mListView.smoothScrollToPosition(i);
                    mListView.onLoadMoreComplete();
                }

                break;
        }
    }

    private void normalRequest() {
        mAdapter = new ChatMsgViewAdapter(this, mDateArrays);
        mListView.setAdapter(mAdapter);
        registerMessageReceiver();
        //添加listview滚动监听
        mListView.setOnLoadMoreListener(new AutoTopLoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mAdapter.getCount() < megNum) {
                    loadMoreData();
                } else {
                    mListView.onLoadMoreComplete();
                }

            }
        });
    }

    /**
     * http://www.eoeandroid.com/thread-242327-2-1.html  21楼问题已解决
     * 哈哈，其实我找到问题了。原来在我的listview中加了android:transcriptMode="alwaysScroll"这个属性，
     * 导致有变动都会回到底部。在下拉刷新的时候，设置[mw_shl_code=java,true]listview.setTranscriptMode
     * (AbsListView.TRANSCRIPT_MODE_DISABLED);[/mw_shl_code]然后计算下原来的位置，数据刷新Adapter.
     * notifyDataSetChanged();接着就是设置listview.setSelection，这样基本是平滑的，没有跳动。体验不错哦
     */


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == com.example.weixindemo.R.id.ll_back) {
            finish();

        } else if (i == com.example.weixindemo.R.id.btn_send) {
            send();
            ((FaceRelativeLayout) findViewById(com.example.weixindemo.R.id.FaceRelativeLayout)).hideFaceView();

        } else if (i == com.example.weixindemo.R.id.iv_sec) {
            PermissionGen.with(ChatGoActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();

            //new PopupWindows(ChatGoActivity.this, btn_photo);
            // 隐藏表情选择框
            //((FaceRelativeLayout) findViewById(com.example.weixindemo.R.id.FaceRelativeLayout)).hideFaceView();

        } else if (i == com.example.weixindemo.R.id.iv_third) {
            PermissionGen.with(ChatGoActivity.this)
                    .addRequestCode(101)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();

            //new PopupWindows(ChatGoActivity.this, btn_photo);
            // 隐藏表情选择框
            //((FaceRelativeLayout) findViewById(com.example.weixindemo.R.id.FaceRelativeLayout)).hideFaceView();

        } else if (i == com.example.weixindemo.R.id.et_sendmessage) {
            //        	mEditTextContent.addTextChangedListener(new TextWatcher() {
//				@Override
//				public void afterTextChanged(Editable s) {
//					String text = mEditTextContent.getText().toString();
//					if(!(text.length()>0)){
//						//Toast.makeText(ChatGoActivity.this,mEditTextContent.getText()+"请输入聊天内容", 2).show();
//						btn_photo.setVisibility(View.VISIBLE);
//						mBtnSend.setVisibility(View.GONE);
//					}else{
//						//Toast.makeText(ChatGoActivity.this,"输入的聊天内容为："+mEditTextContent.getText(), 2).show();
//						btn_photo.setVisibility(View.GONE);
//						mBtnSend.setVisibility(View.VISIBLE);
//					}
//				}
//				@Override
//				public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
//				}
//				@Override
//				public void beforeTextChanged(CharSequence s, int arg1, int arg2,
//						int arg3) {
//				}
//			});
            // 隐藏表情选择框
            ((FaceRelativeLayout) findViewById(com.example.weixindemo.R.id.FaceRelativeLayout)).hideFaceView();
        } else if (i == com.example.weixindemo.R.id.group_set) {
//            Intent intent = new Intent(ChatGoActivity.this,
//                    GroupInfoActivity.class);
//
//            startActivity(intent);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    @PermissionSuccess(requestCode = 100)
    public void doCapture() {
//        PhotoPicker.builder()
//                .setPhotoCount(9)//挑选照片
//                .setShowCamera(false)//不显示拍照功能
//                .setSelected(imgPaths)
//                .setShowGif(true)
//                .setPreviewEnabled(true)
//                .start(ChatGoActivity.this, PhotoPicker.REQUEST_CODE);
    }

    @PermissionFail(requestCode = 100)
    public void doFailedCapture() {
        Toast.makeText(ChatGoActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
    }

    private ImageCaptureManager captureManager;

    @PermissionSuccess(requestCode = 101)
    public void doCamera() {
        try {
            if (captureManager == null) {
                captureManager = new ImageCaptureManager(ChatGoActivity.this);
            }
            Intent intent = captureManager.dispatchTakePictureIntent();
            startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PermissionFail(requestCode = 101)
    public void doFailedCamera() {
        Toast.makeText(ChatGoActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * t0_au_17798_7996:
     * t0_au_17798_7996:
     * t0_au_17798_7996_36729:822
     * t0_au_17798_7996_36729:822
     * t0_au_17798_7996_36737:1697
     * t0_au_17798_7996_36731:hahhah
     * t0_au_17798_7996_36752:
     * t0_au_17798_7996_36751:
     * t0_au_17798_7996_36740:1702
     */
    //发送消息
    public void send() {

        String conString = mEditTextContent.getText().toString();

//        if (conString.length() > 0) {
//            String volleyUrl = Constant.sysUrl + Constant.commitAdd;
//            Log.e("TAG", "发送消息接口地址 " + Constant.sysUrl + Constant.commitAdd);
//            //参数
//            Map<String, String> map = new HashMap<>();
//            map.put(Constant.tableId, chatObjTable);
//            map.put(Constant.pageId, sendMessPageId);
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36729", dataId);
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36737", "1697");//文字类型消息发送参数
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36731", conString);
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36752", "");
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36751", "");
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36740", configRole);//发送者类型教师角色值1703  学员角色值1702
//            //附加参数
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36739", chatObjTable);
//            map.put("strFlag", "appMsg");
//            map.put("userType", StuPra.aliasTitle);//教师user,学员stu
//            map.put("sendType", "4");
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36974", Constant.loginName);
//            map.put("t0_au_" + chatObjTable + "_" + sendMessPageId + "_36975", Constant.USERID);
//            map.put("sessionId", Constant.sessionId);
//
//            Log.e(TAG, "发送消息参数  " + map.toString());
////将消息直接加到列表中
//            mEditTextContent.setText("");//获取文字成功后设置text为空
//            ChatMsgEntity entity = new ChatMsgEntity();
//            entity.setDate(getDate());
//            entity.setName("我");
//            entity.setMsgType(false);
//            entity.setText(conString);
//            mAdapter.addItem(entity);
////			mListView.setSelection(mListView.getCount()-1);
//            //请求
//            OkHttpUtils
//                    .post()
//                    .url(volleyUrl)
//                    .params(map)
//                    .build()
//                    .execute(new EdusStringCallback1(ChatGoActivity.this) {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            ErrorToast.errorToast(mContext, e);
//                            //        dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onResponse1(String response, int id) {
//                            Log.e("ChatActivity", "onResponse: " + "  id  " + response);
//                            // check(response);
//                            if (response != null && response.length() > 0) {
//
////								etContent.setText("");
////								commitData();
//                            }
//                        }
//                    });
//        }
    }

    public String getDate() {
        Calendar c = Calendar.getInstance();
        String year = valueOf(c.get(Calendar.YEAR));
        String month = valueOf(c.get(Calendar.MONTH));
        String day = valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + "-" + hour + ":" + mins);
        return sbBuffer.toString();
    }

    private void updateDisplay(double signalEMA) {

        switch ((int) signalEMA) {
            case 0:
            case 1:
                volume.setImageResource(com.example.weixindemo.R.drawable.amp1);
                break;
            case 2:
            case 3:
                volume.setImageResource(com.example.weixindemo.R.drawable.amp2);

                break;
            case 4:
            case 5:
                volume.setImageResource(com.example.weixindemo.R.drawable.amp3);
                break;
            case 6:
            case 7:
                volume.setImageResource(com.example.weixindemo.R.drawable.amp4);
                break;
            case 8:
            case 9:
                volume.setImageResource(com.example.weixindemo.R.drawable.amp5);
                break;
            case 10:
            case 11:
                volume.setImageResource(com.example.weixindemo.R.drawable.amp6);
                break;
            default:
                volume.setImageResource(com.example.weixindemo.R.drawable.amp7);
                break;
        }
    }


    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            super(mContext);

            View view = View.inflate(mContext, com.example.weixindemo.R.layout.item_popubwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, com.example.weixindemo.R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(com.example.weixindemo.R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, com.example.weixindemo.R.anim.push_bottom_in_2));
            setWidth(LayoutParams.FILL_PARENT);
            setHeight(LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(com.example.weixindemo.R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(com.example.weixindemo.R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(com.example.weixindemo.R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
//				photo();
//				dismiss();
                    Intent intent = new Intent();
                    Intent intent_camera = getPackageManager().getLaunchIntentForPackage("com.android.camera");
                    if (intent_camera != null) {
                        intent.setPackage("com.android.camera");
                    }
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    ChatGoActivity.this.startActivityForResult(intent, TAKE_PICTURE);
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
//				Intent intent = new Intent(ChatGoActivity.this,
//						TestPicActivity.class);
//				startActivity(intent);
//				dismiss();
                /*Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
				startActivity(intent);
				dismiss();*/
                    Intent intent = new Intent(ChatGoActivity.this, ScaleImageFromSdcardActivity.class);
                    ChatGoActivity.this.startActivityForResult(intent, SHOW_ALL_PICTURE);
                    dismiss();
                    overridePendingTransition(com.example.weixindemo.R.anim.in_from_right, com.example.weixindemo.R.anim.out_to_left);//设置切换动画，从右边进入，左边退出
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: 返回 requestCode " + requestCode);
        Log.e(TAG, "onActivityResult: 返回 resultCode " + resultCode);
//        Log.e(TAG, "onActivityResult: 返回 data " + data.toString());
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK && null != data) {
            Log.e(TAG, "onActivityResult: TAKE_PICTURE");
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(ChatGoActivity.this, "未找到SDK", Toast.LENGTH_SHORT).show();
                return;
            }
            new android.text.format.DateFormat();
            String name = android.text.format.DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            Bitmap bitmap;
            String filename = null;
            bitmap = (Bitmap) bundle.get("data");
            FileOutputStream fout = null;
            //定义文件存储路径
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/cloudteam/");
            if (!file.exists()) {
                file.mkdirs();
            }
            filename = file.getPath() + "/" + name;
            try {
                fout = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(ChatGoActivity.this, CameraActivity.class);
            intent.putExtra("camera", filename);
            ChatGoActivity.this.startActivityForResult(intent, SHOW_CAMERA);
        } else if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
//            Log.e(TAG, "onActivityResult: 在聊天页面逐个显示图片");
//            if (data != null) {
//                imgPaths.clear();
//                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                imgPaths.addAll(photos);
////                adapter.notifyDataSetChanged();
//            }
//            img_Paths.clear();
//            img_Paths.addAll(imgPaths);
//
//            for (int i = 0; i < img_Paths.size(); i++) {
//                ChatMsgEntity entity = new ChatMsgEntity();
//                entity.setDate(getDate());
//                entity.setName(StuPra.messageFromName);
//                entity.setMsgType(false);
//                entity.setText(img_Paths.get(i));
//                mDateArrays.add(entity);
//                mAdapter.notifyDataSetChanged();
//                mEditTextContent.setText("");
//                mListView.setSelection(mListView.getCount() - 1);
//                //上传文件
//                File file = new File(img_Paths.get(i));
//
//                uploadMethod(file);
//            }
        }
    }
    public void uploadMethod(File file) {

//        String url = sysUrl + pictureUrl;
//        url = url + "sessionId=" + Constant.sessionId;
//        Log.e(TAG, "uploadMethod: 开始上传文件" + file.getName());
////        if (files.size() > 0) {
//        OkHttpUtils.post()//
//                .addFile("myFile", file.getName(), file)
//                .url(url)
//                .build()
//                .execute(new EdusStringCallback1(ChatGoActivity.this) {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        ErrorToast.errorToast(mContext, e);
////                        waveProgress.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onResponse1(String response, int id) {
//                        try {
//                           String code=getFileCode(response);
//                            sendFile("1753", code);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void inProgress(float progress, long total, int id) {
//
//                    }
//                });
    }
    //加载图片
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        Log.e("imagpath=", imaePath);
        //((ImageView)findViewById(R.id.image)).setImageBitmap(bm);
    }

    private static final int TAKE_PICTURE = 0x000000;
    private static final int SHOW_CAMERA = 0x000001;
    private static final int SHOW_CAMERA_RESULT = 0x000002;
    private String path = "";


    public void photo() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(Environment.getExternalStorageDirectory() + "/myimage/");
            if (!dir.exists()) dir.mkdirs();

            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(dir, valueOf(System.currentTimeMillis())
                    + ".jpg");
            path = file.getPath();
            Uri imageUri = Uri.fromFile(file);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);


        } else {
            Toast.makeText(ChatGoActivity.this, "没有储存卡", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && ((FaceRelativeLayout) findViewById(com.example.weixindemo.R.id.FaceRelativeLayout)).hideFaceView()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static boolean isForeground = false;
    //for receive customer msg from jpush server
    private MessageReceiver myReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";


    public void registerMessageReceiver() {
        myReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(myReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
//                String messge = intent.getStringExtra(StuPra.KEY_MESSAGE);
                String extras = intent.getStringExtra("extras");

//                Log.e(TAG, "onReceive: messge " + messge);
                Log.e(TAG, "onReceive: extras " + extras);
                Log.e(TAG, "onReceive: dataId " + dataId);


                Map<String, Object> extrasMap = JSON.parseObject(extras,
                        new TypeReference<Map<String, Object>>() {
                        });
                Log.e(TAG, "onReceive: extrasMap.get(\"channelId\") " + extrasMap.get("channelId"));

                if (extrasMap.get("channelId").equals(dataId)) {//如果频道相符则

                    ChatMsgEntity entity = new ChatMsgEntity();
                    entity.setDate(valueOf(extrasMap.get("sendTime")));//获取并设置消息发送时间

                    String wantedUserId = valueOf(extrasMap.get("senderId"));//获取userId用来判断是不是自己发的消息
                    Log.e(TAG, "onReceive: wantedUserId " + wantedUserId);
                    Log.e(TAG, "onReceive: wantedUserId " + wantedUserId);
//                    if (!wantedUserId.equals(Config.myName)) {
//                        entity.setName(valueOf(extrasMap.get("sender")));//获取并设置对方名称
//                        entity.setMsgType(true);//true为其他人
//                        entity.setText(messge);//获取并设置消息内容
//                        mDateArrays.add(entity);
//                        mAdapter.notifyDataSetChanged();
//                        mListView.setSelection(mListView.getCount() - 1);
//                    }
//                    else{//自己的消息直接上列表，收到推送后自动过滤，不显示在通知栏
//                        entity.setName(StuPra.messageFromMe);//直接设置自己的名称
//                        entity.setMsgType(false);//false为自己
//                        entity.setText(messge);//获取并设置消息内容
//                        mDateArrays.add(entity);
//                        mAdapter.notifyDataSetChanged();
//                        mListView.setSelection(mListView.getCount() - 1);
//                    }

                }


            }
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(myReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }



}