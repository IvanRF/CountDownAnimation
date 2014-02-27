package ar.com.ivanridao.countdownanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.ivanridao.countdownanimation.CountDownAnimation.CountDownListener;

@SuppressWarnings("unused")
public class TestActivity extends Activity implements CountDownListener {

	private static final int COUNT_DOWN = 10;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        
        TextView textView = (TextView) findViewById(R.id.textView);
        
        final CountDownAnimation countDownAnimation = new CountDownAnimation(textView, COUNT_DOWN);
        countDownAnimation.setCountDownListener(this);
        
        //Change default animation
        //Use scale animation
//        Animation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        countDownAnimation.setAnimation(scaleAnimation);
    	
        //Use a set of animations
//        AnimationSet animationSet = new AnimationSet(false);
//        animationSet.addAnimation(scaleAnimation);
//        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.0f));
//        countDownAnimation.setAnimation(animationSet);
        
        Button createLyricsButton = (Button) findViewById(R.id.button);
        createLyricsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				countDownAnimation.start();
			}
		});
    }
    
    @Override
	public void onCountDownEnd(CountDownAnimation animation) {
		Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
	}

}
