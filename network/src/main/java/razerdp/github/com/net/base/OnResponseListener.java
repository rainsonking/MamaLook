package razerdp.github.com.net.base;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 大灯泡 on 2016/10/27.
 */

public interface OnResponseListener<T> {
    void onStart(int requestType);

    void onSuccess(T response, int requestType);

    void onError(BmobException e, int requestType);

    abstract class SimpleResponseListener<T> implements OnResponseListener<T> {

        @Override
        public void onStart(int requestType) {

        }


        @Override
        public void onError(BmobException e, int requestType) {

        }
    }
}
