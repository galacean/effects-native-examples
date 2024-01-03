package com.example.galacean_effects;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.antgroup.galacean.effects.GEPlayer;

import java.lang.ref.WeakReference;

public class GEPlayDemo extends Activity {

    private static final String TAG = "SimplePlay";

    private LinearLayout mRoot;

    private GEPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mRoot = findViewById(R.id.root);

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.GONE);
                // demo二选一：简单播放/分段播放
                simplePlay();
//                segmentPlay();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer != null) {
            mPlayer.resume();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.destroy();
            mPlayer = null;
        }
    }

    private final static String[] urls = new String[] {
        "https://mdn.alipayobjects.com/mars/afts/file/A*WL2TTZ0DBGoAAAAAAAAAAAAAARInAQ", // 0 heart粒子
        "https://mdn.alipayobjects.com/mars/afts/file/A*D6TbS5ax2TgAAAAAAAAAAAAAARInAQ", // 1 闪电球
        "https://mdn.alipayobjects.com/mars/afts/file/A*TazWSbYr84wAAAAAAAAAAAAAARInAQ", // 2 年兽大爆炸
        "https://mdn.alipayobjects.com/mars/afts/file/A*e7_FTLA_REgAAAAAAAAAAAAAARInAQ", // 3 双十一鼓掌
        "https://mdn.alipayobjects.com/mars/afts/file/A*D4ixTaUS-HoAAAAAAAAAAAAADlB4AQ", // 4 敬业福弹卡
        "https://mdn.alipayobjects.com/mars/afts/file/A*OW2VSKK3bWIAAAAAAAAAAAAADlB4AQ", // 5 七夕福利倒计时
        "https://mdn.alipayobjects.com/mars/afts/file/A*wIkMSokvwCgAAAAAAAAAAAAAARInAQ", // 6 天猫618
        "https://mdn.alipayobjects.com/mars/afts/file/A*VtHiR4iOuxYAAAAAAAAAAAAAARInAQ", // 7 年度账单（40s）
    };

    private void simplePlay() {
        GEPlayer.GEPlayerParams params = new GEPlayer.GEPlayerParams();
        int idx = 4;
        params.url = urls[idx];

        // 降级图
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.CYAN);
        params.downgradeImage = bitmap;

        mPlayer = new GEPlayer(this, params);

        // 用于判断回调时的player是否是当前成员变量player
        WeakReference<GEPlayer> weakPlayer = new WeakReference<>(mPlayer);
        // 防止循环调用，建议使用弱引用
        WeakReference<GEPlayDemo> weakThiz = new WeakReference<>(this);
        mPlayer.loadScene(new GEPlayer.Callback() {
            @Override
            public void onResult(boolean success, String errorMsg) {
                // 一定在UI线程回调
                GEPlayDemo thiz = weakThiz.get();
                if (thiz == null) {
                    return;
                }
                if (weakPlayer.get() != thiz.mPlayer) {
                    return;
                }
                if (!success) {
                    Log.e(TAG, "loadScene fail," + errorMsg);
                    thiz.mPlayer.setX(0);
                    thiz.mPlayer.setY(0);
                    thiz.mPlayer.setLayoutParams(new LinearLayout.LayoutParams((int) thiz.mRoot.getWidth(), (int) thiz.mRoot.getHeight()));
                    thiz.mRoot.addView(thiz.mPlayer);
                    return;
                }

                // 目的是等比拉伸动画view，居中显示，撑满父view的最短边
                float w = thiz.mRoot.getWidth();
                float h = thiz.mRoot.getHeight();
                float x = 0;
                float y = 0;
                float aspect = thiz.mPlayer.getAspect();
                if (w / aspect < h) {
                    h = w / aspect;
                    y = (thiz.mRoot.getHeight() - h) / 2;
                } else {
                    w = h * aspect;
                    x = (thiz.mRoot.getWidth() - w) / 2;
                }

                thiz.mPlayer.setX(x);
                thiz.mPlayer.setY(y);
                thiz.mPlayer.setLayoutParams(new LinearLayout.LayoutParams((int) w, (int) h));
                thiz.mRoot.addView(thiz.mPlayer);

                thiz.mPlayer.play(0, new GEPlayer.Callback() {
                    @Override
                    public void onResult(boolean success, String errorMsg) {
                        GEPlayDemo thiz = weakThiz.get();
                        if (thiz != null && success) {
                            if (thiz.mPlayer != null) {
                                thiz.mRoot.removeView(thiz.mPlayer);
                                thiz.mPlayer.destroy();
                                thiz.mPlayer = null;
                            }
                            Toast.makeText(thiz, "动画播放完毕~", Toast.LENGTH_SHORT).show();
                            Button button = findViewById(R.id.btn);
                            button.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void segmentPlay() {
        GEPlayer.GEPlayerParams params = new GEPlayer.GEPlayerParams();
        params.url = "https://mdn.alipayobjects.com/mars/afts/file/A*1ljVQIC-nfAAAAAAAAAAAAAAARInAQ";
        mPlayer = new GEPlayer(this, params);
        // 用于判断回调时的player是否是当前成员变量player
        WeakReference<GEPlayer> weakPlayer = new WeakReference<>(mPlayer);
        // 防止循环调用，建议使用弱引用
        WeakReference<GEPlayDemo> weakThiz = new WeakReference<>(this);
        mPlayer.loadScene(new GEPlayer.Callback() {
            @Override
            public void onResult(boolean success, String errorMsg) {
                // 一定在UI线程回调
                GEPlayDemo thiz = weakThiz.get();
                if (thiz == null) {
                    return;
                }
                if (weakPlayer.get() != thiz.mPlayer) {
                    return;
                }
                if (!success) {
                    Log.e(TAG, "loadScene fail," + errorMsg);
                    return;
                }

                // 目的是等比拉伸动画view，居中显示，撑满父view的最短边
                float w = thiz.mRoot.getWidth();
                float h = thiz.mRoot.getHeight();
                float x = 0;
                float y = 0;
                float aspect = thiz.mPlayer.getAspect();
                if (w / aspect < h) {
                    h = w / aspect;
                    y = (thiz.mRoot.getHeight() - h) / 2;
                } else {
                    w = h * aspect;
                    x = (thiz.mRoot.getWidth() - w) / 2;
                }

                thiz.mPlayer.setX(x);
                thiz.mPlayer.setY(y);
                thiz.mPlayer.setLayoutParams(new ViewGroup.LayoutParams((int) w, (int) h));
                thiz.mRoot.addView(thiz.mPlayer);

                thiz.mPlayer.play(0, 59, -1, null);
                Toast.makeText(thiz, "循环播第一段，点击可播第二段", Toast.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GEPlayDemo thiz = weakThiz.get();
                        thiz.mPlayer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                GEPlayDemo thiz = weakThiz.get();
                                if (thiz == null) {
                                    return;
                                }

                                thiz.mPlayer.setOnClickListener(null);
                                Toast.makeText(thiz, "开始播第二段", Toast.LENGTH_LONG).show();
                                thiz.mPlayer.play(60 , 239, 0, new GEPlayer.Callback() {
                                    @Override
                                    public void onResult(boolean success, String errorMsg) {
                                        GEPlayDemo thiz = weakThiz.get();
                                        if (thiz != null && success && thiz.mPlayer != null) {
                                            thiz.mRoot.removeView(thiz.mPlayer);
                                            thiz.mPlayer.destroy();
                                            thiz.mPlayer = null;
                                        }

                                        Toast.makeText(thiz, "播放结束", Toast.LENGTH_LONG).show();

                                        Button button = findViewById(R.id.btn);
                                        button.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    }
                }, 800);
            }
        });
    }
}
