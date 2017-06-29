package kehuhuaPage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

import kehuhuaPage.widgts.CnToolbar;
import razerdp.friendcircle.R;


/**
 * Created by Administrator on 2017/6/29 0029.
 *
 */

public class HomePageFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        CnToolbar cnToolbar = (CnToolbar) view.findViewById(R.id.stu_toolbar);
        cnToolbar.setTitle("主页");
        initLocalBanner(view);
        return view;
    }
    @Override
    public void onClick(View v) {

    }
/*
  加载本地图片
 */
    private void initLocalBanner(View view) {

        FlyBanner mBannerLocal = (FlyBanner) view.findViewById(R.id.banner_home_page);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.home_page_banner_img_1);
        images.add(R.drawable.home_page_banner_img_2);
        images.add(R.drawable.home_page_banner_img_3);
        images.add(R.drawable.home_page_banner_img_4);
        mBannerLocal.setImages(images);
//        mBannerLocal.setPoinstPosition(FlyBanner.RIGHT);//设定点点点的相对位置

        mBannerLocal.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                toast("点击了第"+(position+1)+"张图片");
            }
        });
    }

    private void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
