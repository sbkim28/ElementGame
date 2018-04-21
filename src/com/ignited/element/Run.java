package com.ignited.element;

import com.ignited.element.element.ElementManager;
import com.ignited.element.element.ElementQuiz;

import java.util.Locale;

public class Run {

    public static final int time = 10;
    public static final int life = 3;

    public static void main(String[] args) {
        ElementQuiz quiz = new ElementQuiz(time,life, Locale.KOREA, ElementManager.get(Locale.KOREA));
        quiz.execute();
    }

}
