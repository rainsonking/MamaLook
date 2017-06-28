package kehuhuaPage.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kehuhuaPage.fragments.HomePageFragment;
import kehuhuaPage.fragments.LookSoonFragment;
import kehuhuaPage.fragments.MeFragment;
import kehuhuaPage.fragments.StudyFragment;
import kehuhuaPage.ui.StuFragmentTabAdapter;
import razerdp.friendcircle.R;
import razerdp.friendcircle.app.FriendCircleApp;


/**

 */
public class StuMainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "StuMainActivity";
    StuFragmentTabAdapter stutabAdapter;
    private RadioGroup radioGroup;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_main);
//        getSupportActionBar().hide();
        FriendCircleApp.getInstance().addActivity(this);
        initView();
        initFragment();
    }


    @Override
    public void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ((RadioButton) radioGroup.findViewById(R.id.radio1)).setChecked(true);// 设置radiogroup的机制
    }


    public void initFragment() {
        Fragment homePageFragment = new HomePageFragment();
        Fragment studyFragment = new StudyFragment();
        Fragment lookSoonFragment = new LookSoonFragment();
        Fragment meFragment = new MeFragment();
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(homePageFragment);
//        mFragments.add(menuFragment);
        mFragments.add(studyFragment);
        mFragments.add(lookSoonFragment);
        mFragments.add(meFragment);


        stutabAdapter = new StuFragmentTabAdapter(this, mFragments, R.id.content, radioGroup);

        stutabAdapter.setOnRgsExtraCheckedChangedListener(new StuFragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                // TODO Auto-generated method stub
                super.OnRgsExtraCheckedChanged(radioGroup, checkedId, index);
                switch (checkedId) {
//                    case R.id.radio0:
//                        mToolbar.setTitle("首页");
//                        break;
                    case R.id.radio1:
//                        mToolbar.setTitle("首页");
                        break;
                    case R.id.radio2:
//                        mToolbar.setTitle("课表");
                        break;
                    case R.id.radio3:
//                        mToolbar.setTitle("消息");

                        break;
                    case R.id.radio4:
//                        mToolbar.setTitle("我的");

                        break;

                }
            }
        });
    }

//    public void fragmentClick() {
//        radio3.setChecked(true);
//    }




    private static long exitTime = 0;// 退出时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {

                String msg = "再按一次退出";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                exitTime = System.currentTimeMillis();
            } else {
                Toast.makeText(this, "直接退出", Toast.LENGTH_SHORT).show();
                FriendCircleApp.getInstance().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View view) {

    }

}


/**
 * Code	描述	详细解释
 6001	无效的设置，tag/alias 不应参数都为 null
 6002	设置超时	建议重试
 6003	alias 字符串不合法	有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|
 6004	alias超长。最多 40个字节	中文 UTF-8 是 3 个字节
 6005	某一个 tag 字符串不合法	有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|
 6006	某一个 tag 超长。一个 tag 最多 40个字节	中文 UTF-8 是 3 个字节
 6007	tags 数量超出限制。最多 1000个	这是一台设备的限制。一个应用全局的标签数量无限制。
 6008	tag 超出总长度限制	总长度最多 7K 字节
 6009	未知错误	由于权限问题，导致的PushService启动异常。
 6011	10s内设置tag或alias大于10次	短时间内操作过于频繁
 6012	在JPush服务stop状态下设置了tag或alias	3.0.0版本新增的错误码。开发者可根据这个错误码的信息做相关处理或者提示。
 -997	注册失败	（一般是由于没有网络造成的）如果确保设备网络正常，还是一直遇到此问题，则还有另外一个原因：JPush 服务器端拒绝注册。而这个的原因一般是：你当前的 App 的 Android 包名，以及 appKey ，与你在 Portal 上注册的应用的 Android 包名与 AppKey 不相同。
 1005	包名和AppKey 不匹配
 1008	AppKey非法	请到官网检查此应用详情中的appkey，确认无误
 1009	当前的appkey下没有创建Android应用。	请到官网检查此应用的应用详情
 -996	网络连接断开	如果确保设备网络正常，可能是由于包名不正确，服务器强制断开客户端的连接。
 -994	网络连接超时
 CrashLo
 */
