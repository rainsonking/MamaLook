package razerdp.friendcircle.ui.widget.praisewidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.widget.TextView;

import java.util.List;

import razerdp.friendcircle.R;
import razerdp.friendcircle.app.mvp.model.entity.LikesInfo;
import razerdp.friendcircle.app.mvp.model.entity.UserInfo;
import razerdp.github.com.baseuilib.widget.span.CustomImageSpan;
import razerdp.github.com.baseuilib.widget.span.SpannableStringBuilderCompat;
import razerdp.github.com.baseuilib.widget.span.ClickableSpanEx;

/**
 * Created by 大灯泡 on 2016/2/21.
 * 点赞显示控件
 */
public class PraiseWidget extends TextView {
    private static final String TAG = "PraiseWidget";

    //点赞名字展示的默认颜色
    private int textColor = 0xff517fae;
    //点赞列表心心默认图标
    private int iconRes = R.drawable.icon_like;
    //默认字体大小
    private int textSize = 14;
    //默认点击背景
    private int clickBg = 0x00000000;

    private List<LikesInfo> datas;

    private static final LruCache<String, SpannableStringBuilderCompat> praiseCache
            = new LruCache<String, SpannableStringBuilderCompat>(50) {
        @Override
        protected int sizeOf(String key, SpannableStringBuilderCompat value) {
            return 1;
        }
    };

    public PraiseWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PraiseWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PraiseWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PraiseWidget);
        textColor = a.getColor(R.styleable.PraiseWidget_font_color, 0xff517fae);
        textSize = a.getDimensionPixelSize(R.styleable.PraiseWidget_font_size, 14);
        clickBg = a.getColor(R.styleable.PraiseWidget_click_bg_color, 0x00000000);
        iconRes = a.getResourceId(R.styleable.PraiseWidget_like_icon, R.drawable.icon_like);
        a.recycle();
        //如果不设置，clickableSpan不能响应点击事件
        this.setMovementMethod(LinkMovementMethod.getInstance());
        setOnTouchListener(new ClickableSpanEx.ClickableSpanSelector());
        setTextSize(textSize);
    }

    public void setDatas(List<LikesInfo> datas) {
        this.datas = datas;
    }

    @Override
    public boolean onPreDraw() {
        if (datas == null || datas.size() == 0) {
            return super.onPreDraw();
        }
        else {
            createSpanStringBuilder(datas);
            return true;
        }
    }

    private void createSpanStringBuilder(List<LikesInfo> datas) {
        if (datas == null || datas.size() == 0) return;
        String key = Integer.toString(datas.hashCode() + datas.size());
        SpannableStringBuilderCompat spanStrBuilder = praiseCache.get(key);
        if (spanStrBuilder == null) {
            CustomImageSpan icon = new CustomImageSpan(getContext(), iconRes);
            //因为spanstringbuilder不支持直接append span，所以通过spanstring转换
            SpannableString iconSpanStr = new SpannableString(" ");
            iconSpanStr.setSpan(icon, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            spanStrBuilder = new SpannableStringBuilderCompat(iconSpanStr);
            //给出两个空格，点赞图标后
            spanStrBuilder.append(" ");
            for (int i = 0; i < datas.size(); i++) {
                PraiseClick praiseClick = new PraiseClick.Builder(getContext(), datas.get(i).getUserInfo()).setTextSize(textSize)
                                                                                             .setColor(textColor)
                                                                                             .setClickEventColor(clickBg)
                                                                                             .build();
                try {
                    spanStrBuilder.append(datas.get(i).getUserInfo().getNick(), praiseClick, 0);
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Log.e(TAG, "praiseUserInfo是空的哦");
                }
                if (i != datas.size() - 1) spanStrBuilder.append(", ");
                else spanStrBuilder.append("\0");
            }
            praiseCache.put(key, spanStrBuilder);
        }
        setText(spanStrBuilder);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        praiseCache.evictAll();
        if (praiseCache.size() == 0) {
            Log.d(TAG, "clear cache success!");
        }
    }
}
