/*
 * Copyright (C) 2014 Ivan Ridao Freitas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ar.com.ivanridao.countdownanimation;

import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class CountDownAnimation {

	private TextView mTextView;
    private Animation mAnimation;
    private int mStartCount;
    private int mCurrentCount;
    private CountDownListener mListener;
    
    private Handler mHandler = new Handler();

    private final Runnable mCountDown = new Runnable() {
        public void run() {
        	if (mCurrentCount > 0) {
        		mTextView.setText(mCurrentCount+"");
        		mTextView.startAnimation(mAnimation);
	        	mCurrentCount--;
	       	} else {
	       		mTextView.setVisibility(View.GONE);
	       		if (mListener != null)
	       			mListener.onCountDownEnd(CountDownAnimation.this);
	       	}
        }
    };
    
	public CountDownAnimation(TextView textView, int startCount) {
		this.mTextView = textView;
		this.mStartCount = startCount;
		
		mAnimation = new AlphaAnimation(1.0f, 0.0f);
		mAnimation.setDuration(1000);
	}
    
	public void start() {
		mHandler.removeCallbacks(mCountDown);
		
		mTextView.setText(mStartCount+"");
		mTextView.setVisibility(View.VISIBLE);
		
		mCurrentCount = mStartCount;
		
		mHandler.post(mCountDown);
		for (int i = 1; i <= mStartCount; i++) {
			mHandler.postDelayed(mCountDown, i * 1000);
		}
	}
	
	public void setAnimation(Animation animation) {
		this.mAnimation = animation;
		if(mAnimation.getDuration() == 0)
			mAnimation.setDuration(1000);
	}
	
	public void setCountDownListener(CountDownListener listener) {
        mListener = listener;
    }
	
	/**
     * <p>A count down listener receives notifications from a count down animation.
     * Notifications indicate count down animation related events, such as the end
     * of the animation.</p>
     */
	public static interface CountDownListener {
		/**
         * <p>Notifies the end of the count down animation.</p>
         *
         * @param animation The count down animation which reached its end.
         */
	    void onCountDownEnd(CountDownAnimation animation);
	}
}
