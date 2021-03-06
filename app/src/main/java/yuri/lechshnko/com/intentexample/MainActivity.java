package yuri.lechshnko.com.intentexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import yuri.lechshnko.com.intentexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    private MainContract.Presenter presenter;
    private ActivityMainBinding binding;
    private final int REQUEST_CODE = 1004;
    private final String REQUEST_ACTION = "REQUEST_ACTION";
    private final String RESULT_ACTION = "RESULT_ACTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainPresenter();
        binding.setEvent(presenter);
    }

    @Override
    public void setImage(Bitmap image) {
        binding.imageMain.setImageBitmap(image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                if (data != null && data.getByteArrayExtra(RESULT_ACTION) != null){
                    //String base64 = data.getStringExtra(RESULT_ACTION);
                    byte[] response = data.getByteArrayExtra(RESULT_ACTION);
                }
            }
        }
    }

    @Override
    public void toast(String messag) {
        Toast.makeText(this,messag,Toast.LENGTH_LONG).show();
    }

    @Override
    public void sendImage(byte[] base64) {
        startActivityForResult(new Intent()
            .setClassName("com.example.androidreciveintentexample",
                   "com.example.androidreciveintentexample.MainActivity")
            .putExtra(REQUEST_ACTION, base64), REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startView(this);
        presenter.init();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null){
            presenter.stopView();
            presenter = null;
        }
        super.onDestroy();
    }
}
