package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import java.util.Locale;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Use seconds, running and wasRunning respectively
    // to record the number of seconds passed,
    // whether the stopwatch is running and
    // whether the stopwatch was running
    // before the activity was paused.

    // Number of seconds displayed
    // on the stopwatch.
    private int seconds = 0;
    private int breakTime = 15;
    private int restTime = 60;
    private int repCountStart = 3;
    private int repCountEnd = 7;
    private int repCount = 0;

    private boolean wasRunning;

    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }



        repCount = repCountStart;
        setRepText(repCount);

        runTimer();
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

    // Save the state of the stopwatch
    // if it's about to be destroyed.
    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    // If the activity is paused,
    // stop the stopwatch.
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Start the stopwatch running
    // when the Start button is clicked.
    // Below method gets called
    // when the Start button is clicked.
    public void onClickStart(android.view.View view)
    {
        running = true;
    }

    // Stop the stopwatch running
    // when the Stop button is clicked.
    // Below method gets called
    // when the Stop button is clicked.
    public void onClickStop(android.view.View view)
    {
        running = false;
    }

    // Reset the stopwatch when
    // the Reset button is clicked.
    // Below method gets called
    // when the Reset button is clicked.
    public void onClickReset(android.view.View view)
    {
        running = true;
        repCount++;

        if (repCount > repCountEnd)
        {
            seconds = restTime;
            repCount = repCountStart;
            setRepText("Break!");
        }else
        {
            setRepText(repCount);
            seconds = breakTime;
        }

    }

    public void onClickResetActual(View view)
    {
        running = false;
        repCount = repCountStart;
        setRepText(repCount);
        seconds = 0;
    }

    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private void runTimer()
    {

        // Get the text view.
        final TextView timeView
                = (TextView)findViewById(
                R.id.time_view);

        final TextView repCountText
                = (TextView)findViewById(
                R.id.rep_count);


        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {


                // Set the text view text.
                timeView.setText(String.format("%02d", seconds));

                if (seconds == 0)
                {
                    setActivityBackgroundColor(Color.GREEN);
                    timeView.setBackgroundColor(Color.GREEN);

                    if (running)
                    {
                        running = false;

                        if (repCount == repCountStart)
                        {
                            setRepText(repCount);
                        }
                    }


                }else
                {
                    setActivityBackgroundColor(Color.RED);
                    timeView.setBackgroundColor(Color.RED);
                }

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds--;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
}
