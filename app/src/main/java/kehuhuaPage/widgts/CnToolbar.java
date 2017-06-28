package kehuhuaPage.widgts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import razerdp.friendcircle.R;


/**
 * Created by Ivan on 15/9/28.
 *
 */
public class CnToolbar extends Toolbar {


    private View mView;
    private TextView mTextTitle;
    private EditText mSearchView;
    private ImageButton mRightImageButton,mLeftImageButton;
    private ImageView mOften_drop,mOften_collect;
    public CnToolbar(Context context) {
       this(context,null);
    }

    public CnToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CnToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



        initView();
        setContentInsetsRelative(10,10);




        if(attrs !=null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.CnToolbar, defStyleAttr, 0);

            boolean isShowRightButton = a.getBoolean(R.styleable.CnToolbar_isOnShowRightButton,false);
            if(isShowRightButton){
                showRightView();
            }else{
                hideRightView();
            }
            a.recycle();
        }

    }

    private void initView() {


        if(mView == null) {

            LayoutInflater mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);


            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);


            mRightImageButton = (ImageButton) mView.findViewById(R.id.toolbar_rightButton);
            mLeftImageButton = (ImageButton) mView.findViewById(R.id.toolbar_leftButton);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }
    }


    public  void setRightButtonOnClickListener(OnClickListener li){

        mRightImageButton.setOnClickListener(li);
    }
    public  void setLeftButtonOnClickListener(OnClickListener li){

        mLeftImageButton.setOnClickListener(li);
    }



    @Override
    public void setTitle(int resId) {

        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if(mTextTitle !=null) {
            mTextTitle.setText(title);
            showTitleView();
        }
    }



    public  void showSearchView(){

        if(mSearchView !=null)
            mSearchView.setVisibility(VISIBLE);

    }


    public void hideSearchView(){
        if(mSearchView !=null)
            mSearchView.setVisibility(GONE);
    }


    public  void showRightView(){

        if(mRightImageButton !=null)
            mRightImageButton.setVisibility(VISIBLE);

    }


    public void hideRightView(){
        if(mRightImageButton !=null)
            mRightImageButton.setVisibility(GONE);
    }


    public void showOftenDrop(){
        if(mOften_drop !=null)
            mOften_drop.setVisibility(VISIBLE);
    }

    public void showOftenCollect(){
        if(mOften_collect !=null)
            mOften_collect.setVisibility(VISIBLE);
    }

    public void hideOftenDrop(){
        if(mOften_drop !=null)
            mOften_drop.setVisibility(GONE);
    }

    public void hideOftenCollect(){
        if(mOften_collect !=null)
            mOften_collect.setVisibility(GONE);
    }


    public void showTitleView(){
        if(mTextTitle !=null)
            mTextTitle.setVisibility(VISIBLE);
    }

    public void hideTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(GONE);

    }

    public void  setLeftButtonIcon(Drawable icon){

        if(mLeftImageButton !=null){

            mLeftImageButton.setImageDrawable(icon);
            mLeftImageButton.setVisibility(VISIBLE);
        }

    }

    public void  setRightButtonIcon(Drawable icon){

        if(mRightImageButton !=null){
            mRightImageButton.setImageDrawable(icon);
        }

    }

}