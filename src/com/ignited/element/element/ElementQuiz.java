package com.ignited.element.element;

import com.ignited.element.data.DataManager;
import com.ignited.element.element.Element;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ElementQuiz{

    private Thread timer;
    private Thread player;

    private final Object lock = new Object();

    private int time;
    private int life;
    private int score;

    private List<Element> elements;

    private Properties properties;

    private State state;

    private BufferedReader reader;
    private Random random;

    private static final String Q = "question";
    private static final String SYMBOL = "question.symbol";
    private static final String NAME = "question.name";
    private static final String NUMBER = "question.number";

    private static final String WRONG = "answer.wrong";
    private static final String CORRECT = "answer.correct";
    private static final String TIMEOUT = "answer.timeout";

    private static final String STATE = "state";
    private static final String GAMEOVER = "gameover";


    public ElementQuiz(int time, int life, List<Element> elements) {
        this(time, life, Locale.ENGLISH, elements);
    }

    public ElementQuiz(int time, int life, Locale locale, List<Element> elements) {
        this.time = time;
        this.life = life;
        this.elements = new ArrayList<>(elements);
        properties = DataManager.loadProp("String_" + locale.getLanguage() + ".properties", "utf-8");
        state = State.READY;
    }

    private void initTimer(){
        if(timer != null && !timer.isInterrupted()){
            timer.interrupt();
        }

        timer = new Thread(() -> {
            boolean flag = true;
            long i = System.currentTimeMillis();
            while (System.currentTimeMillis() < time * 1000 + i){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                player.interrupt();
            }
        });


    }

    public void execute(){
        if(state != State.READY){
            return;
        }
        while (true) {
            state = State.SOLVING;
            if (reader == null) {
                reader = new BufferedReader(new InputStreamReader(System.in));
            }

            final String answer = initQuestion();

            player = new Thread(() -> {
                try {
                    while (!reader.ready()) {
                        Thread.sleep(10);
                    }
                    timer.interrupt();
                    String pAnswer = reader.readLine().trim();
                    state = answer.equals(pAnswer) ? State.CORRECT : State.WRONG;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    state = State.TIMEOUT;
                }
            });

            initTimer();
            player.start();
            timer.start();
            try {
                player.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String s = "";
            switch (state) {
                case TIMEOUT:
                    s = String.format(properties.getProperty(TIMEOUT), answer);
                    --life;
                    break;
                case WRONG:
                    s = String.format(properties.getProperty(WRONG), answer);
                    --life;
                    break;
                case CORRECT:
                    s = properties.getProperty(CORRECT);
                    ++score;
                    break;
                default:
                    throw new RuntimeException();
            }
            System.out.println(s);
            System.out.println(String.format(properties.getProperty(STATE), score, life));
            System.out.println("= = = = = = = = = =");
            if(life == 0){
                System.out.println(properties.getProperty(GAMEOVER));
                state = State.FINISHED;
                break;
            }
        }
    }

    private String initQuestion(){
        if(random == null){
            random = new Random();
        }
        Collections.shuffle(elements, random);
        Element element = elements.get(0);
        String tmp = "";
        String first = "";
        String second = "";
        String last = "";
        switch (random.nextInt(5)){
            case 0 :
                first = SYMBOL;
                second = NUMBER;
                last = element.getSymbol();
                tmp = String.valueOf(element.getNumber());
                break;
            case 1:
                first = NAME;
                second = NUMBER;
                last = element.getName();
                tmp = String.valueOf(element.getNumber());
                break;
            case 2:
                first = NUMBER;
                second = NAME;
                last = String.valueOf(element.getNumber());
                tmp = String.valueOf(element.getName());
                break;
            case 3:
                first = SYMBOL;
                second = NAME;
                last = element.getSymbol();
                tmp = String.valueOf(element.getName());
                break;
            case 4:
                first = NUMBER;
                second = SYMBOL;
                last = String.valueOf(element.getNumber());
                tmp = String.valueOf(element.getSymbol());
                break;
            case 5:
                first = NAME;
                second = SYMBOL;
                last = element.getName();
                tmp = String.valueOf(element.getSymbol());
                break;
        }
        String question = String.format(
                properties.getProperty(Q), properties.getProperty(first),
                properties.getProperty(second), last
        );
        System.out.println(question);
        return tmp;
    }

    private enum State {
        READY, SOLVING, TIMEOUT, CORRECT, WRONG, FINISHED
    }
}
