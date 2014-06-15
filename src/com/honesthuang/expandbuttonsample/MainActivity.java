package com.honesthuang.expandbuttonsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private Button mButtonMain,mButton1,mButton2,mButton3,mButton4;
	private boolean mExpanded = false;
	private int mLeftMargin,mTopMargin;//初始化按钮位置
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	
	private void initView(){
		mButtonMain = (Button) findViewById(R.id.button_main);
		mButtonMain.setOnClickListener(this);
		
		mLeftMargin = ((FrameLayout.LayoutParams)mButtonMain.getLayoutParams()).leftMargin;
		mTopMargin = ((FrameLayout.LayoutParams)mButtonMain.getLayoutParams()).topMargin;
		
		mButton1 = (Button) findViewById(R.id.button1);
		mButton2 = (Button) findViewById(R.id.button2);
		mButton3 = (Button) findViewById(R.id.button3);
		mButton4 = (Button) findViewById(R.id.button4);
		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);
		mButton3.setOnClickListener(this);
		mButton4.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.button_main: {
			Animation ani = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
			ani.setDuration(500);
			view.startAnimation(ani);

			if (!mExpanded) {
				openButton(mButton1, 0, 4, 300);
				openButton(mButton2, 1, 4, 300);
				openButton(mButton3, 2, 4, 300);
				openButton(mButton4, 3, 4, 300);
				mExpanded = true;
			} else {
				closeButton(mButton1, 0, 4, 300);
				closeButton(mButton2, 1, 4, 300);
				closeButton(mButton3, 2, 4, 300);
				closeButton(mButton4, 3, 4, 300);
				mExpanded = false;
			}
			break;
		}
		default: {
			Toast.makeText(MainActivity.this, "id:" + view.getId(),
					Toast.LENGTH_SHORT).show();
			break;
		}
		}

	}
	
	private void openButton(final View view,int index,int total,int radius){
		view.setVisibility(View.VISIBLE);
		
		double angle = Math.PI/2/(total-1)*index;
		final int x = (int) (Math.cos(angle)*radius);
		final int y = (int) (Math.sin(angle)*radius);
		Animation ani = new TranslateAnimation(0, x, 0, y);
		ani.setDuration(500);
		ani.setFillAfter(true);
		ani.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {}
			@Override
			public void onAnimationRepeat(Animation arg0) {}
			@Override
			public void onAnimationEnd(Animation ani) {
				FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
				params.leftMargin += x;
				params.topMargin += y;
				view.setLayoutParams(params);
				view.clearAnimation();
			}
		});
		view.clearAnimation();
		view.startAnimation(ani);
	}
	
	private void closeButton(final View view,int index,int total,int radius){
		double angle = Math.PI/2/(total-1)*index;
		final int x = (int) (Math.cos(angle)*radius);
		final int y = (int) (Math.sin(angle)*radius);
		AnimationSet set = new AnimationSet(true);
		
		Animation trans = new TranslateAnimation(0, -x, 0, -y);
		set.addAnimation(trans);
		
		Animation alpha = new AlphaAnimation(1f, 0f);
		set.addAnimation(alpha);
		
		set.setDuration(500);
		set.setFillAfter(true);
		
		set.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {}
			
			@Override
			public void onAnimationEnd(Animation ani) {
				FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
				params.leftMargin = mLeftMargin;
				params.topMargin = mTopMargin;
				view.setLayoutParams(params);
				view.clearAnimation();
				view.setVisibility(View.GONE);
			}
		});
		view.startAnimation(set);
	}
}
