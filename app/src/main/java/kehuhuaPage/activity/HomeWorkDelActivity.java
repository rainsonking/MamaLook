package kehuhuaPage.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import kehuhuaPage.fragments.HomeWorkFragment;
import kehuhuaPage.fragments.LearnFocusFragment;
import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class HomeWorkDelActivity extends BaseActivity implements View.OnClickListener {
    private RadioGroup rg_tab;
    private FragmentTransaction ft;
    private FrameLayout fragment_content;
    private Fragment learnFocusFragment, homeWorkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeworkdel);
        initView();
        learnFocusFragment = new LearnFocusFragment();
        homeWorkFragment = new HomeWorkFragment();
        // 進入系統默認為movie
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_content, learnFocusFragment);
        ft.commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {
        rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
        fragment_content = (FrameLayout) findViewById(R.id.fragment_content);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                ft = getSupportFragmentManager().beginTransaction();
                if (checkedId == R.id.rb_1) {
                    ft.replace(R.id.fragment_content, learnFocusFragment);
                } else {
                    ft.replace(R.id.fragment_content, homeWorkFragment);
                }
                ft.commit();
            }
        });
    }
}
