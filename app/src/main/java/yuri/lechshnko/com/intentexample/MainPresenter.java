package yuri.lechshnko.com.intentexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import static android.content.res.AssetManager.ACCESS_BUFFER;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private Bitmap image;

    public MainPresenter() {
    }

    @Override
    public void startView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void stopView() {
        if (view != null) view = null;
        if (image != null) image = null;
    }

    @Override
    public void init() {
        if (App.getContext() != null) {
            getImage().subscribe(new DisposableSingleObserver<Bitmap>() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    view.setImage(image = bitmap);
                    dispose();
                }

                @Override
                public void onError(Throwable e) {
                    if (e.getMessage() != null){
                        view.toast("Error ".concat(e.getMessage()));
                    }else {
                        view.toast("Error method getImage Presenter");
                    }
                }
            });
        } else {
            view.toast("Error App.getContext() == null");
        }
    }

    private Single<Bitmap> getImage() {
        return Single.defer(() -> {
            InputStream stream = App.getContext().getAssets().open("unnamed.png", ACCESS_BUFFER);
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            Bitmap image = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            return Single.just(image);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sendImage() {
        if (image != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            view.sendImage(encoded);
        }else {
            view.toast("Error image null method send Presenter");
        }
    }
}
