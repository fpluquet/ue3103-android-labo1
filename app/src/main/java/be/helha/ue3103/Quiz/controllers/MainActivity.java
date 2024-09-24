package be.helha.ue3103.Quiz.controllers;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import be.helha.ue3103.Quiz.R;
import be.helha.ue3103.Quiz.models.Quiz;


public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private Button mResetButton;

    private Quiz mQuiz = new Quiz();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        retrieveViews();
        setCurrentQuestionText();
        setScoreText();
        toggleButtonsIfLastQuestion();
        setListeners();
        Log.d("test", "jj");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void toggleButtonsIfLastQuestion() {
        if(mQuiz.isFinished()) {
            mResetButton.setVisibility(Button.VISIBLE);
            mNextButton.setVisibility(Button.GONE);
        } else {
            mResetButton.setVisibility(Button.GONE);
            mNextButton.setVisibility(Button.VISIBLE);
        }
    }

    private void retrieveViews() {
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);

        mQuestionTextView = findViewById(R.id.question_text_view);
        mNextButton = findViewById(R.id.next_button);
        mScoreTextView = findViewById(R.id.score_text_view);
        mResetButton = findViewById(R.id.reset_button);
    }

    private void setListeners() {
        mNextButton.setOnClickListener(v -> {
            mQuiz.nextQuestion();
            setCurrentQuestionText();
            enableButtons();
            toggleButtonsIfLastQuestion();
        });

        mFalseButton.setOnClickListener(v -> {
            this.checkAnswer(false);
        });

        mTrueButton.setOnClickListener(v -> {
            this.checkAnswer(true);
        });

        mResetButton.setOnClickListener(v -> {
            mQuiz.reset();
            setCurrentQuestionText();
            setScoreText();
            enableButtons();
            toggleButtonsIfLastQuestion();
        });
    }

    private void setCurrentQuestionText() {
        mQuestionTextView.setText(mQuiz.getCurrentQuestion().getTextResId());
    }


    private void disableButtons() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private void enableButtons() {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private void checkAnswer(boolean answer) {
        if(mQuiz.checkAnswer(answer)) {
            Toast.makeText(MainActivity.this, R.string.good_answer, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();
        }
        mQuiz.updateScore(answer);
        setScoreText();
        disableButtons();
        toggleButtonsIfLastQuestion();
    }

    private void setScoreText() {
        mScoreTextView.setText(getString(R.string.score_text, mQuiz.getScore(), mQuiz.getQuestionCount()));
    }
}