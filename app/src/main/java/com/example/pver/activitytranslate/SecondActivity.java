package com.example.pver.activitytranslate;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * @version : 1.0
 * @Description :
 * @autho : dongyiming
 * @data : 2017/8/19 21:07
 */
public class SecondActivity extends AppCompatActivity {


    private RelativeLayout rlayout_yifei;
    private RelativeLayout rlayout_xingye;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        rlayout_yifei = (RelativeLayout) findViewById(R.id.rlayout_yifei);
        rlayout_xingye = (RelativeLayout) findViewById(R.id.rlayout_xingye);

        String value = getIntent().getStringExtra("key");
        if (value != null && value.equals("single")) {
            loadBackground(R.drawable.img_yifei, rlayout_yifei);
            rlayout_xingye.setVisibility(View.GONE);
        } else if (value != null && value.equals("multi")) {
            loadBackground(R.drawable.img_yifei, rlayout_yifei);
            loadBackground(R.drawable.img_xingye, rlayout_xingye);
            rlayout_xingye.setVisibility(View.VISIBLE);
        } else if (value != null && value.equals("explode")) {
            getWindow().setEnterTransition(new Explode().setDuration(2000));
            getWindow().setReturnTransition(new Fade());
        }
    }

    private Visibility buildReturnTransition() {

        Visibility visibility = new Explode();
        visibility.setDuration(5000);
        // 修饰动画，定义动画的变化率
        visibility.setInterpolator(new AccelerateInterpolator());
        return visibility;
    }

    /**
     * 单个view共享
     *
     * @param mActivity
     * @param view
     * @param transName
     */
    public static void startIntent(Activity mActivity, View view, String transName) {

        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, transName).toBundle();
        Intent intent = new Intent();
        intent.setClass(mActivity, SecondActivity.class);
        intent.putExtra("key", "single");
        mActivity.startActivity(intent, bundle);
    }

    public static void startIntent(Activity mActivity,String type) {

        Intent intent = new Intent(mActivity, SecondActivity.class);
        intent.putExtra("key", type);
        mActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
    }

    /**
     * 多个共享
     *
     * @param mActivity
     * @param view1
     * @param view2
     */
    public static void startIntent(Activity mActivity, View view1, View view2) {

        Pair first = new Pair<>(view1, ViewCompat.getTransitionName(view1));
        Pair second = new Pair<>(view2, ViewCompat.getTransitionName(view2));
        Intent intent = new Intent();
        intent.setClass(mActivity, SecondActivity.class);
        intent.putExtra("key", "multi");
        //可以传多个view，可变参数
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity, first, second);
        ActivityCompat.startActivity(mActivity, intent, activityOptions.toBundle());
    }

    /**
     * 添加背景高斯模糊,模糊参数自己调试
     *
     * @param resourceId
     * @param relativeLayout
     */
    private void loadBackground(int resourceId, final RelativeLayout relativeLayout) {

        Glide.with(SecondActivity.this).load(resourceId)
                .bitmapTransform(new BlurTransformation(this, 23, 15))
                .listener(new RequestListener<Integer, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            relativeLayout.setBackground(resource.getCurrent());
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //和普通的跳转不一样,所以不能直接使用finish();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("dongyiming", "dowm");
            finishAfterTransition();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
