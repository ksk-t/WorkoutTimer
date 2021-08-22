package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Locale;
import android.view.View;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    // Number of seconds displayed
    // on the stopwatch.
    private int breakTime = 15;
    private int restTime = 60;
    private int repCountStart = 3;
    private int repCountEnd = 7;
    private int repCount = 0;

    private MyCountDownTimer countDownTimer;
    MediaPlayer beepSound321;
    MediaPlayer beepSound0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownTimer = new MyCountDownTimer(breakTime * 1000, 1);
        beepSound321 = MediaPlayer.create(getApplicationContext(), R.raw.sound321);
        beepSound0 = MediaPlayer.create(getApplicationContext(), R.raw.sound0);

        repCount = repCountStart;
        setRepText(repCount);

        final TextView timeView
                = (TextView)findViewById(
                R.id.time_view);

        timeView.setText("GO");

    }

    private void setRepText(int value)
    {
        final TextView repCountText
                = (TextView)findViewById(
                R.id.rep_count);

        repCountText.setText("Reps: " + value);
    }

    private void setRepText(String value)
    {
        final TextView repCountText
                = (TextView)findViewById(
                R.id.rep_count);

        repCountText.setText(value);
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    // Reset the stopwatch when
    // the Reset button is clicked.
    // Below method gets called
    // when the Reset button is clicked.
    public void onClickReset(android.view.View view)
    {
        repCount++;

        if (repCount > repCountEnd)
        {
            repCount = repCountStart;
            setRepText("Rest");
            if (countDownTimer != null)
            {
                countDownTimer.cancel();
                countDownTimer = new MyCountDownTimer(restTime * 1000, 1000);
            }
        }else if (repCount == repCountStart + 1)
        {
            setRepText(repCount);
            if (countDownTimer != null)
            {
                countDownTimer.cancel();
                countDownTimer = new MyCountDownTimer(breakTime * 1000, 1000);
            }

        }else
        {
            setRepText(repCount);
        }

        countDownTimer.start();
    }

    public void onClickResetActual(View view)
    {
        repCount = repCountStart;
        setRepText(repCount);
        countDownTimer.cancel();

        final TextView timeView
                = (TextView)findViewById(
                R.id.time_view);

        timeView.setText("GO");
    }

    public class MyCountDownTimer extends CountDownTimer
    {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000) + 1;
            final TextView timeView
                = (TextView)findViewById(
                R.id.time_view);

            timeView.setText(Integer.toString(progress));

            if (progress <= 3)
            {
                beepSound321.start();
            }

            final ProgressBar progressCircle
                    = (ProgressBar)findViewById(
                    R.id.progress_circle);

            progressCircle.setProgress((int)(millisUntilFinished / breakTime ));
        }

        @Override
        public void onFinish() {
            final TextView timeView
                    = (TextView)findViewById(
                    R.id.time_view);

            timeView.setText("GO");

            if (repCount == repCountStart)
            {
                setRepText(repCount);
            }

            final ProgressBar progressCircle
                    = (ProgressBar)findViewById(
                    R.id.progress_circle);

            progressCircle.setProgress(0);

            beepSound0.start();

        }
    }
}
