package kehuhuaPage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kehuhuaPage.activity.ContTeacherActivity;
import kehuhuaPage.activity.EventNoticeActivity;
import kehuhuaPage.activity.PrinMailBoxActivity;
import kehuhuaPage.widgts.CnToolbar;
import razerdp.friendcircle.R;
import razerdp.friendcircle.activity.FriendCircleDemoActivity;


/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class LookSoonFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.ll_stu_class_circle)
    LinearLayout llStuClassCircle;
    @Bind(R.id.ll_contact_teacher)
    LinearLayout llContactTeacher;
    @Bind(R.id.ll_principal_mailbox)
    LinearLayout llPrincipalMailbox;
    @Bind(R.id.ll_event_notification)
    LinearLayout llEventNotification;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look_soon, container, false);
        ButterKnife.bind(this, view);
        CnToolbar cnToolbar = (CnToolbar) view.findViewById(R.id.stu_toolbar);
        cnToolbar.setTitle("洛信");

        llStuClassCircle.setOnClickListener(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_stu_class_circle, R.id.ll_contact_teacher, R.id.ll_principal_mailbox, R.id.ll_event_notification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_stu_class_circle:
                Intent intent = new Intent(getActivity(), FriendCircleDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_contact_teacher:
                intent = new Intent(getActivity(), ContTeacherActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_principal_mailbox:
                intent = new Intent(getActivity(), PrinMailBoxActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_event_notification:
                intent = new Intent(getActivity(), EventNoticeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
