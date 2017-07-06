package kehuhuaPage.fragments;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import kehuhuaPage.widgts.CnToolbar;
import razerdp.friendcircle.R;


/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class MeFragment extends Fragment implements View.OnClickListener {

//    @Bind(R.id.stu_head_image)
//    SimpleDraweeView stuHeadImage;
    @Bind(R.id.stu_name)
    TextView stuName;
    @Bind(R.id.stu_phone)
    TextView stuPhone;
    @Bind(R.id.stu_phone_line)
    LinearLayout stuPhoneLine;
    @Bind(R.id.stu_school_area)
    TextView stuSchoolArea;
    @Bind(R.id.stu_info_image)
    ImageView stuInfoImage;
    @Bind(R.id.stu_info_data)
    LinearLayout stuInfoData;
    @Bind(R.id.stu_account_info_image)
    ImageView stuAccountInfoImage;
    @Bind(R.id.stu_account_info)
    RelativeLayout stuAccountInfo;
    @Bind(R.id.stu_suggest_image)
    ImageView stuSuggestImage;
    @Bind(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @Bind(R.id.ll_stu_feedback)
    LinearLayout llStuFeedback;
    @Bind(R.id.stu_passWord_image)
    ImageView stuPassWordImage;
    @Bind(R.id.stu_resetPwd)
    RelativeLayout stuResetPwd;
    @Bind(R.id.stu_version_image)
    ImageView stuVersionImage;
    @Bind(R.id.stu_version)
    TextView stuVersion;
    @Bind(R.id.ll_stu_version_check)
    LinearLayout llStuVersionCheck;
    @Bind(R.id.stu_clear_cache)
    ImageView stuClearCache;
    @Bind(R.id.tv_clean_cache)
    TextView tvCleanCache;
    @Bind(R.id.ll_stu_clear_cache)
    LinearLayout llStuClearCache;
    @Bind(R.id.layout)
    LinearLayout layout;
    @Bind(R.id.stu_log_out)
    TextView stuLogOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        CnToolbar cnToolbar = (CnToolbar) view.findViewById(R.id.stu_toolbar);
        cnToolbar.setTitle("我");
        ButterKnife.bind(this, view);
        Resources r = null;
//        Uri uri = Uri.parse(Constant.sysUrl + Constant.downLoadFileStr + Constant.teaMongoId);
//        stuHeadImage.setImageURI(uri);
//        Bitmap image = ((BitmapDrawable)stuHeadImage.getDrawable()).getBitmap();
//        saveImageToGallery(getActivity(), image);

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
