package kehuhuaPage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kehuhuaPage.widgts.CnToolbar;
import razerdp.friendcircle.R;
import razerdp.friendcircle.activity.FriendCircleDemoActivity;


/**
 * Created by Administrator on 2017/6/29 0029.
 *
 */

public class LookSoonFragment extends Fragment implements View.OnClickListener {
    LinearLayout stu_class_circle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look_soon, container, false);

        CnToolbar cnToolbar = (CnToolbar) view.findViewById(R.id.stu_toolbar);
        cnToolbar.setTitle("洛信");
        stu_class_circle= (LinearLayout) view.findViewById(R.id.stu_class_circle);
        stu_class_circle.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stu_class_circle:
                Intent intent = new Intent(getActivity(), FriendCircleDemoActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
