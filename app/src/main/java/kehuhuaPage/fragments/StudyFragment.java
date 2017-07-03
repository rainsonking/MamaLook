package kehuhuaPage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kehuhuaPage.activity.HomeWorkActivity;
import kehuhuaPage.coursecard.CourseCardActivity;
import kehuhuaPage.widgts.CnToolbar;
import razerdp.friendcircle.R;


/**
 * Created by Administrator on 2017/6/29 0029.
 * 
 */

public class StudyFragment extends Fragment implements View.OnClickListener {
    private TextView tvCourse, tvCoursewrokpro;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        CnToolbar cnToolbar = (CnToolbar) view.findViewById(R.id.stu_toolbar);
        cnToolbar.setTitle("学习");
//        Bitmap image = ((BitmapDrawable)stuHeadImage.getDrawable()).getBitmap();
//        saveImageToGallery(getActivity(), image);
        tvCoursewrokpro = (TextView) view.findViewById(R.id.tv_coursewrokpro);
        tvCourse = (TextView) view.findViewById(R.id.tv_course);
        tvCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseCardActivity.class);
                startActivity(intent);
            }
        });
        tvCoursewrokpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeWorkActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
