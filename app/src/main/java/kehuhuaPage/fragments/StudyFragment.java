package kehuhuaPage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kehuhuaPage.widgts.CnToolbar;
import razerdp.friendcircle.R;


/**
 * Created by Administrator on 2017/6/29 0029.
 *
 */

public class StudyFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        CnToolbar cnToolbar = (CnToolbar) view.findViewById(R.id.stu_toolbar);
        cnToolbar.setTitle("学习");
//        Bitmap image = ((BitmapDrawable)stuHeadImage.getDrawable()).getBitmap();
//        saveImageToGallery(getActivity(), image);

        return view;
    }
    @Override
    public void onClick(View v) {

    }
}
