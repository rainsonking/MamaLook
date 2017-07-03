package kehuhuaPage.activity;

import android.os.Bundle;
import android.widget.ListView;

import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class CourseworkProActivity extends BaseActivity {
    private ListView lvCourseworkpro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseworkpro);
        initView();
    }

    @Override
    public void initView() {
        lvCourseworkpro = (ListView) findViewById(R.id.lv_courseworkpro);
    }
}
