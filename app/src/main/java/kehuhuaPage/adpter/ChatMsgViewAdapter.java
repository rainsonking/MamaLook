package kehuhuaPage.adpter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.weixindemo.ChatMsgEntity;
import com.example.weixindemo.FaceConversionUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kehuhuaPage.widgts.Utils;
import razerdp.friendcircle.R;

public class ChatMsgViewAdapter extends BaseAdapter {

    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;
        int IMVT_TO_MSG = 1;
    }

    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();
    private List<ChatMsgEntity> coll;
    private LayoutInflater mInflater;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private Context ctx;
    Resources res;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll) {
        ctx = context;
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
        res = context.getResources();
    }

    @Override
    public int getCount() {
        return coll.size();
    }

    @Override
    public Object getItem(int position) {
        return coll.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMsgEntity entity = coll.get(position);
        if (entity.getMsgType()) {
            return IMsgViewType.IMVT_COM_MSG;
        } else {
            return IMsgViewType.IMVT_TO_MSG;
        }
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public View getView(final int postion, View converView, ViewGroup parent) {
        final ChatMsgEntity entity = coll.get(postion);
        boolean isComMsg = entity.getMsgType();
        ViewHolder viewHolder = null;
        if (converView == null) {
            viewHolder = new ViewHolder();
            if (isComMsg) {
                converView = mInflater.inflate(com.example.weixindemo.R.layout.chat_left, null);
                viewHolder.ll_chatcontent = (LinearLayout) converView.findViewById(com.example.weixindemo.R.id.ll_chat_content);
                viewHolder.tv_ccontent = (TextView) converView.findViewById(com.example.weixindemo.R.id.tv_ccontent);
                viewHolder.ll_sel_more = (LinearLayout) converView.findViewById(com.example.weixindemo.R.id.ll_sel_more);
                viewHolder.iv_left = (SimpleDraweeView) converView.findViewById(com.example.weixindemo.R.id.iv_left);

            } else {
                converView = mInflater.inflate(com.example.weixindemo.R.layout.chat_right, null);
                viewHolder.iv_right = (SimpleDraweeView) converView.findViewById(com.example.weixindemo.R.id.iv_right);
            }

            viewHolder.tvSendTime = (TextView) converView.findViewById(com.example.weixindemo.R.id.tv_sendtime);
            viewHolder.tvContent = (TextView) converView.findViewById(com.example.weixindemo.R.id.tv_chatcontent);
            viewHolder.tvTime = (TextView) converView.findViewById(com.example.weixindemo.R.id.tv_time);
            viewHolder.tvUserName = (TextView) converView.findViewById(com.example.weixindemo.R.id.tv_username);

            ViewHolder.isComMsg = isComMsg;
            converView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.tvSendTime.setText(entity.getDate());


        if (entity.getText().contains(".amr")) {
            viewHolder.tvTime.setText(entity.getTime());
            viewHolder.tvContent.setText("");
            viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, com.example.weixindemo.R.drawable.chatto_voice_playing, 0);
        } else if(entity.getText().contains(".jpg")){

            viewHolder.tvContent.setText("");
            final File file = new File(entity.getText());
            Uri uri= Uri.fromFile(file);
            Bitmap bitmap= getBitmapFromUri(uri);
            Drawable drawable =new BitmapDrawable(bitmap);
            viewHolder.tvContent.setBackground(drawable);
            viewHolder.tvContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFile(file);
                }
            });
//            viewHolder.tvContent.setWidth(100);
//            viewHolder.tvContent.setHeight(100);

        } else {
            SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(ctx, entity.getText());
            if (isComMsg) {
                if (entity.getCttype().equals("1") && !entity.getPage().equals("0") && !entity.getTable().equals("")) {
                    viewHolder.tvContent.setVisibility(View.GONE);
                    viewHolder.ll_chatcontent.setVisibility(View.VISIBLE);
                    viewHolder.tv_ccontent.setText(spannableString);
                } else if (entity.getCttype().equals("11") && !entity.getPage().equals("0") && !entity.getMain().equals("null")) {
                    viewHolder.tvContent.setVisibility(View.GONE);
                    viewHolder.ll_chatcontent.setVisibility(View.VISIBLE);
                    viewHolder.tv_ccontent.setText(spannableString);
                } else {
                    viewHolder.tvContent.setVisibility(View.VISIBLE);
                    viewHolder.ll_chatcontent.setVisibility(View.GONE);

//			viewHolder.tvContent.setText(entity.getText());
//			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    viewHolder.tvContent.setText(spannableString);
                }
                viewHolder.ll_sel_more.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickItem(entity, postion);
                    }
                });

                Resources r = ctx.getResources();
                Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + r.getResourcePackageName(R.drawable.default_head_image) + "/"
                        + r.getResourceTypeName(R.drawable.default_head_image) + "/"
                        + r.getResourceEntryName(R.drawable.default_head_image));
                viewHolder.iv_left.setImageURI(uri);
            } else {
                viewHolder.tvContent.setText(spannableString);
                viewHolder.tvContent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickItem(entity, postion);
                    }
                });
//                StuPra.reqHeadPort(ctx, Constant.sysUrl + Constant.meiJiaAlDownLoadFileStr + Constant.F_MONGOID, viewHolder.iv_right);

//                Resources  r =ctx.getResources();
//                Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                        + r.getResourcePackageName(R.drawable.default_head_image) + "/"
//                        + r.getResourceTypeName(R.drawable.default_head_image) + "/"
//                        + r.getResourceEntryName(R.drawable.default_head_image));
//                viewHolder.iv_right.setImageURI(uri);
//                viewHolder.iv_right.setImageURI(Constant.chatGoPerImgUri);
            }
            viewHolder.tvTime.setText(entity.getTime());
        }

//        viewHolder.tvContent.setOnClickListener(new OnClickListener() {
//        viewHolder.ll_sel_more.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onClickItem(entity, postion);
//            }
//        });
        viewHolder.tvUserName.setText(entity.getName());
        return converView;
    }

    private void onClickItem(ChatMsgEntity entity, int postion) {
//        Log.e(TAG, "onClick: entity " + entity.toString());
//        if (entity.getCttype().equals("1") && !entity.getPage().equals("0") && !entity.getTable().equals("")) {
//            Log.e(TAG, "onClick: 1");
//            Map<String, Object> itemMap = new HashMap<>();
//            itemMap.put("menuName", "穿透消息");
//            itemMap.put("tableId", entity.getTable());
//            itemMap.put("pageId", entity.getPage());
//            String itemMapStr = JSON.toJSONString(itemMap);
//            Intent intent = new Intent();
//            intent.setClass(ctx, ListActivity4.class);
//            intent.putExtra("itemData", itemMapStr);
//            ctx.startActivity(intent);
//
//        } else if (entity.getCttype().equals("11") && !entity.getPage().equals("0") && !entity.getMain().equals("null")) {
//            Log.e(TAG, "onClick: 2");
//            Map<String, Object> itemMap = new HashMap<>();
//            itemMap.put("buttonName", "");
//            itemMap.put("tableId", entity.getTable());
//            itemMap.put("pageId", entity.getPage());
//            itemMap.put("mainId", entity.getMain());
//            Intent intent = new Intent();
//            intent.setClass(ctx, PushOnlyReadActivity.class);
//            String itemMapStr = JSON.toJSONString(itemMap);
//            intent.putExtra("itemSet", itemMapStr);
//            ctx.startActivity(intent);
//
//        } else if (entity.getText().contains(".amr")) {
//            Log.e(TAG, "onClick: 3");
//            playMusic(android.os.Environment.getExternalStorageDirectory() + "/" + entity.getText());
//        } else if (entity.getText().contains(".jpg")) {
//            Log.e(TAG, "onClick: 4");
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            // String id=entity.getText().toString();
//            bundle.putInt("ID", postion);
//            intent.putExtras(bundle);
//            intent.setClass(ctx, PhotoActivity.class);
//            ctx.startActivity(intent);
//        }
//                    else {
//                        Log.e(TAG, "onClick: 5");
//                        Intent intent = new Intent();
//                        Bundle bundle = new Bundle();
//                        String text = entity.getText();
//                        bundle.putString("text", text);
//                        intent.putExtras(bundle);
//                        intent.setClass(ctx, TextReadActivity.class);
//                        ctx.startActivity(intent);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
    }

    static class ViewHolder {
        public TextView
                tvSendTime,
                tvUserName,
                tvContent,
                tvTime, tv_ccontent;
        public LinearLayout ll_chatcontent, ll_sel_more;
        public SimpleDraweeView iv_left, iv_right;
        public static boolean isComMsg = true;

    }

    /**
     * @param name
     * @Description
     */
    private void playMusic(String name) {
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(name);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //添加数据

    /**
     * 下拉刷新更新数据
     */
    public void addItem(ChatMsgEntity mDataItem) {

        if (mDataItem != null) {
            coll.add(mDataItem);
            notifyDataSetChanged();
        }
    }

    /**
     * 下拉刷新更新数据
     */
    public void addData(List<ChatMsgEntity> mDataItem) {

        addData(0, mDataItem);
    }

    /**
     * 上拉加载添加数据的方法
     */
    public void addData(int position, List<ChatMsgEntity> mDataItem) {
        if (mDataItem != null && mDataItem.size() > 0) {
            coll.addAll(position, mDataItem);
            notifyDataSetChanged();
        }

    }
    private Bitmap getBitmapFromUri(Uri uri)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 打开文件
     *
     * @param file
     */
    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = Utils.getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转
        ctx.startActivity(Intent.createChooser(intent, "请选择打开方式"));//每次打开都询问打开方式，这样会避免不能修改默认程序
    }
}
