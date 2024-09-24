package be.helha.ue3103.Quiz.models;

import be.helha.ue3103.Quiz.R;

public class Quiz {

    private Question[] mQuestionBank;
    private int mCurrentIndex = 0;
    private int score = 0;
    private boolean hasAnswered = false;

    public Quiz() {
        this.mQuestionBank = new Question[] {
                new Question(R.string.question_australia, false),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),

        };
    }

    public Question getCurrentQuestion() {
        return mQuestionBank[mCurrentIndex];
    }

    public void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        hasAnswered = false;
    }

    public boolean checkAnswer(boolean answer) {
        return mQuestionBank[mCurrentIndex].isAnswerTrue() == answer;
    }

    public void updateScore(boolean answer) {
        if(hasAnswered) {
            return;
        }
        if(checkAnswer(answer)) {
            score++;
        }
        hasAnswered = true;
    }

    public int getScore() {
        return score;
    }

    public int getQuestionCount() {
        return mQuestionBank.length;
    }

    public boolean isFinished() {
        return mCurrentIndex == mQuestionBank.length - 1;
    }

    public void reset() {
        mCurrentIndex = 0;
        score = 0;
        hasAnswered = false;
    }
}
