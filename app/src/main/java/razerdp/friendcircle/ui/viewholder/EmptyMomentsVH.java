package razerdp.friendcircle.ui.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import razerdp.friendcircle.app.mvp.model.entity.MomentsInfo;


/**
 * Created by 大灯泡 on 2016/11/3.
 *
 * 空内容的vh
 *
 * @see razerdp.friendcircle.config.MomentsType
 */

public class EmptyMomentsVH extends CircleBaseViewHolder {

    public EmptyMomentsVH(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindView(@NonNull View rootView) {

    }

    @Override
    public void onBindDataToView(@NonNull MomentsInfo data, int position, int viewType) {

    }
}
