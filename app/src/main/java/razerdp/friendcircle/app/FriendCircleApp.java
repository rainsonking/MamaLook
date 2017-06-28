package razerdp.friendcircle.app;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import razerdp.friendcircle.app.manager.LocalHostManager;
import razerdp.friendcircle.config.Define;
import razerdp.github.com.baselibrary.base.AppContext;
import razerdp.github.com.baselibrary.helper.AppFileHelper;
import razerdp.github.com.baselibrary.helper.AppSetting;
import razerdp.github.com.baselibrary.manager.localphoto.LocalPhotoManager;

/**
 * Created by 大灯泡 on 2016/10/26.
 * <p>
 * app
 */

public class FriendCircleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initARouter();
        initBmob();
        initLocalHostInfo();
        AppFileHelper.initStoryPath();
        LocalPhotoManager.INSTANCE.registerContentObserver(null);
        if (AppSetting.loadBooleanPreferenceByKey(AppSetting.APP_HAS_SCAN_IMG, false)) {
            LocalPhotoManager.INSTANCE.scanImgAsync(null);
        }
    }

    private void initBmob() {
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId(Define.BMOB_APPID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(15)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(1800)
                .build();
        Bmob.initialize(config);
    }




    private List<Activity> activityList = new LinkedList<>();
    private static FriendCircleApp instance;
    private void initLocalHostInfo() {
        LocalHostManager.INSTANCE.init();
    }
    //单例设计模式中取得唯一的MyApplication实例
    public static FriendCircleApp getInstance(){
        if(instance == null)
            instance = new FriendCircleApp();
        return instance;
    }

    //添加activity到容器中
    public void addActivity(Activity activity){
        activityList.add(activity);
    }
    //遍历所有的activity并finish
    public void exitApp(){
        for(Activity activity : activityList){
            if(activity != null)
                activity.finish();
        }
        System.exit(0);
    }
}
