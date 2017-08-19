package com.example.pver.activitytranslate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_single;
    private Button btn_multi;
    private Button btn_custom;
    private ImageView img_yifei;
    private ImageView img_xingye;
    private Button btn_explode;
    private Button btn_slide;
    private Button btn_fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Shared Element 共享元素特效,两个界面共享的View布局需要相同的android:transitionName
        btn_single = (Button) findViewById(R.id.btn_single);
        btn_multi = (Button) findViewById(R.id.btn_multi);
        btn_custom = (Button) findViewById(R.id.btn_custom);
        btn_explode = (Button) findViewById(R.id.btn_explode);
        btn_slide = (Button) findViewById(R.id.btn_slide);
        btn_fade = (Button) findViewById(R.id.btn_fade);
        img_yifei = (ImageView) findViewById(R.id.img_yifei);
        img_xingye = (ImageView) findViewById(R.id.img_xingye);
        btn_single.setOnClickListener(this);
        btn_multi.setOnClickListener(this);
        btn_custom.setOnClickListener(this);
        btn_explode.setOnClickListener(this);
        btn_slide.setOnClickListener(this);
        btn_fade.setOnClickListener(this);

        setAnimation();
    }

    private void setAnimation() {

        getWindow().setEnterTransition(new Slide().setDuration(1500));
        // 启动新 Activity ,此页面退出的动画
        getWindow().setExitTransition(new Explode());
        // 重新进入的动画。即第二次进入，可以和首次进入不一样。
        getWindow().setReenterTransition(new Slide().setDuration(1500));
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_single) {

            SecondActivity.startIntent(MainActivity.this, img_yifei, "liuyifei");
        } else if (view.getId() == R.id.btn_multi) {

            SecondActivity.startIntent(MainActivity.this, img_yifei, img_xingye);
        } else if (view.getId() == R.id.btn_custom) {

        } else if (view.getId() == R.id.btn_explode || view.getId() == R.id.btn_slide || view.getId() == R.id.btn_fade) {//中心退出

            SecondActivity.startIntent(MainActivity.this, "explode");
        }
    }

    /**
     * 两种定义方式
     * 1,通过java代码设置一系列参数
     * 2,通过xml注册transition,参数在里面设置，然后TransitionInflater.from(this).inflateTransition(R.transition.explode)来得到
     *
     * @return
     */
    private Visibility buildReturnTransition() {

        Visibility visibility = new Explode();
        visibility.setDuration(1000);
        return visibility;
    }
}
