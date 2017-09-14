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

package com.ivanrf.countdownanimation;

import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * Defines a count down animation to be shown on a {@link TextView }.
 * 
 * @author Ivan Ridao Freitas
 * 
 */
public class CountDownAnimation {

	private TextView mTextView;
	private Animation mAnimation;
	private int mStartCount;
	private int mCurrentCount;
	private CountDownListener mListener;
    	private CountDownProgressListener mProgressListener;

	private Handler mHandler = new Handler();

	private final Runnable mCountDown = new Runnable() {
		public void run() {
			if (mCurrentCount > 0) {
				mTextView.setText(mCurrentCount + "");
				mTextView.startAnimation(mAnimation);
				mCurrentCount--;
				
				if(mProgressListener!=null)
                    			mProgressListener.onCountDownProgress(CountDownAnimation.this);
			} else {
				mTextView.setVisibility(View.GONE);
				if (mListener != null)
					mListener.onCountDownEnd(CountDownAnimation.this);
			}
		}
	};

	/**
	 * <p>
	 * Creates a count down animation in the <var>textView</var>, starting from
	 * <var>startCount</var>.
	 * </p>
	 * <p>
	 * By default, the class defines a fade out animation, which uses
	 * {@link AlphaAnimation } from 1 to 0.
	 * </p>
	 * 
	 * @param textView
	 *            The view where the count down will be shown
	 * @param startCount
	 *            The starting count number
	 */
	public CountDownAnimation(TextView textView, int startCount) {
		this.mTextView = textView;
		this.mStartCount = startCount;

		mAnimation = new AlphaAnimation(1.0f, 0.0f);
		mAnimation.setDuration(1000);
	}

	/**
	 * Starts the count down animation.
	 */
	public void start() {
		mHandler.removeCallbacks(mCountDown);

		mTextView.setText(mStartCount + "");
		mTextView.setVisibility(View.VISIBLE);

		mCurrentCount = mStartCount;

		mHandler.post(mCountDown);
		for (int i = 1; i <= mStartCount; i++) {
			mHandler.postDelayed(mCountDown, i * 1000);
		}
	}

	/**
	 * Cancels the count down animation.
	 */
	public void cancel() {
		mHandler.removeCallbacks(mCountDown);

		mTextView.setText("");
		mTextView.setVisibility(View.GONE);
	}

	/**
	 * Sets the animation used during the count down. If the duration of the
	 * animation for each number is not set, one second will be defined.
	 */
	public void setAnimation(Animation animation) {
		this.mAnimation = animation;
		if (mAnimation.getDuration() == 0)
			mAnimation.setDuration(1000);
	}

	/**
	 * Returns the animation used during the count down.
	 */
	public Animation getAnimation() {
		return mAnimation;
	}

	/**
	 * Sets a new starting count number for the count down animation.
	 * 
	 * @param startCount
	 *            The starting count number
	 */
	public void setStartCount(int startCount) {
		this.mStartCount = startCount;
	}

	/**
	 * Returns the starting count number for the count down animation.
	 */
	public int getStartCount() {
		return mStartCount;
	}

    	/**
     	* Returns current count left for the count down animation
     	* @return
     	*/
    	public int getCurrentCount() {
        	return mCurrentCount;
    	}
    
	/**
	 * Binds a listener to this count down animation. The count down listener is
	 * notified of events such as the end of the animation.
	 * 
	 * @param listener
	 *            The count down listener to be notified
	 */
	public void setCountDownListener(CountDownListener listener) {
		mListener = listener;
	}

	/**
	 * A count down listener receives notifications from a count down animation.
	 * Notifications indicate count down animation related events, such as the
	 * end of the animation.
	 */
	public static interface CountDownListener {
		/**
		 * Notifies the end of the count down animation.
		 * 
		 * @param animation
		 *            The count down animation which reached its end.
		 */
		void onCountDownEnd(CountDownAnimation animation);
	}
	

    	/**
     	* Binds a listener to this count down animation. The count down listener is
     	* notified of events such as the progress of the animation.
     	*
     	* @param progressListener
     	*            The count down listener to be notified
     	*/
    	public void setCountDownProgressListener(CountDownProgressListener progressListener) {
     	   mProgressListener = progressListener;
    	}

    	/**
    	* A count down progress listener receives notifications from a count down animation.
     	* Notifications indicate count down animation related events, such as the
     	* progress of the animation.
     	*/
    	public static interface CountDownProgressListener {
        	/**
         	* Notifies the progress of the count down animation.
         	*
         	* @param animation
         	*            The count down animation which reached its next step.
         	*/
        	void onCountDownProgress(CountDownAnimation animation);
    	}
}
