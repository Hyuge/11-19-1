package com.example.h.fuxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    /**
     * 跳过
     */
    private Button mBt;
    private ImageView mIv;
    /**
     * 5
     */
    private TextView mTv;
    private String string[]={"4","3","2","1"};
    private int in=0;
    Handler handler=new Handler (){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                if (in<string.length){
                    mTv.setText (string[in++]);
                    handler.sendEmptyMessageDelayed (1,1000);
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ( );
    }

    private void initView() {
        mBt = (Button) findViewById (R.id.bt);
        mBt.setOnClickListener (this);
        mIv = (ImageView) findViewById (R.id.iv);
        mTv = (TextView) findViewById (R.id.tv);
        handler.sendEmptyMessageDelayed (1,1000);
        Animation animation = AnimationUtils.loadAnimation (this, R.anim.anim);
        animation.setAnimationListener (this);
        mIv.setAnimation (animation);
        animation.start ();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ( )) {
            default:
                break;
            case R.id.bt:
                startActivity (new Intent (MainActivity.this,Main2Activity.class));
                finish ();
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity (new Intent (MainActivity.this,Main2Activity.class));
        finish ();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
