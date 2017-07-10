package kehuhuaPage.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import kehuhuaPage.adpter.PrinMailBoxBaseAdapter;
import razerdp.friendcircle.R;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class PrinMailBoxActivity extends BaseActivity {

    ListView lvListview;
    PrinMailBoxBaseAdapter adapter;
    private List<Map<String,Object>> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prin_mainbox);

        initView();
        initData();
    }

    private void initData() {
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("area","北京");
        map.put("school","国贸校区");
        map.put("phone","18526256256");
        map.put("email","185262@163.com");
        lists.add(map);
        map =new HashMap<String,Object>();
        map.put("area","北京");
        map.put("school","望京校区");
        map.put("phone","18526256256");
        map.put("email","185262@163.com");
        lists.add(map);
        adapter=new PrinMailBoxBaseAdapter(lists,this);
        lvListview.setAdapter(adapter);
    }

    @Override
    public void initView() {
        lvListview= (ListView) findViewById(R.id.lv_listview);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
