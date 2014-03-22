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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanrf.countdownanimation.CountDownAnimation.CountDownListener;

public class TestActivity extends Activity implements CountDownListener {

	private TextView textView;
	private EditText startCountEditText;
	private Spinner spinner;

	private CountDownAnimation countDownAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		textView = (TextView) findViewById(R.id.textView);
		startCountEditText = (EditText) findViewById(R.id.startCountEditText);

		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.animations_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		Button startButton = (Button) findViewById(R.id.button);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startCountDownAnimation();
			}
		});

		Button cancelButton = (Button) findViewById(R.id.buttonCancel);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelCountDownAnimation();
			}
		});

		initCountDownAnimation();
	}

	private void initCountDownAnimation() {
		countDownAnimation = new CountDownAnimation(textView, getStartCount());
		countDownAnimation.setCountDownListener(this);
	}

	private void startCountDownAnimation() {
		// Customizable animation
		if (spinner.getSelectedItemPosition() == 1) { // Scale
			// Use scale animation
			Animation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
					0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			countDownAnimation.setAnimation(scaleAnimation);
		} else if (spinner.getSelectedItemPosition() == 2) { // Set (Scale +
																// Alpha)
			// Use a set of animations
			Animation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
					0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
			AnimationSet animationSet = new AnimationSet(false);
			animationSet.addAnimation(scaleAnimation);
			animationSet.addAnimation(alphaAnimation);
			countDownAnimation.setAnimation(animationSet);
		}

		// Customizable start count
		countDownAnimation.setStartCount(getStartCount());

		countDownAnimation.start();
	}

	private void cancelCountDownAnimation() {
		countDownAnimation.cancel();
	}

	private int getStartCount() {
		return Integer.parseInt(startCountEditText.getText().toString());
	}

	@Override
	public void onCountDownEnd(CountDownAnimation animation) {
		Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
	}
}
