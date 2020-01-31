package yuri.lechshnko.com.intentexample;

import android.graphics.Bitmap;

public interface MainContract {
    interface View{
        void setImage(Bitmap image);

        void toast(String messag);

        void sendImage(String base64);
    }

    interface Presenter extends BasePresenter<View>{
        void init();

        void sendImage();
    }
}
