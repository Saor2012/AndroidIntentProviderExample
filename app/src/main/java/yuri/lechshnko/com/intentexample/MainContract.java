package yuri.lechshnko.com.intentexample;

import android.graphics.Bitmap;

import io.reactivex.Single;

public interface MainContract {
    interface View{
        void setImage(Bitmap image);

        void toast(String messag);

        void sendImage(byte[] base64);
    }

    interface Presenter extends BasePresenter<View>{
        void init();

        void sendImage();

        Single<Bitmap> getImage(byte[] byteArray);
    }
}
