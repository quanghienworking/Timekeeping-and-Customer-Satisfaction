package com.timelinekeeping.modelMCS;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.model.EmotionCompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/13/2016.
 */
public class EmotionRecognizeScores {
    private Double anger = 0d;
    private Double contempt = 0d;
    private Double disgust = 0d;
    private Double fear = 0d;
    private Double happiness = 0d;
    private Double neutral = 0d;
    private Double sadness = 0d;
    private Double surprise = 0d;

    public EmotionRecognizeScores() {
    }

    public EmotionRecognizeScores(Double anger, Double contempt, Double disgust, Double fear,
                                  Double happiness, Double neutral, Double sadness, Double surprise) {
        this.anger = anger;
        this.contempt = contempt;
        this.disgust = disgust;
        this.fear = fear;
        this.happiness = happiness;
        this.neutral = neutral;
        this.sadness = sadness;
        this.surprise = surprise;
    }

    public Double getAnger() {
        return anger;
    }

    public void setAnger(Double anger) {
        this.anger = anger;
    }

    public Double getContempt() {
        return contempt;
    }

    public void setContempt(Double contempt) {
        this.contempt = contempt;
    }

    public Double getDisgust() {
        return disgust;
    }

    public void setDisgust(Double disgust) {
        this.disgust = disgust;
    }

    public Double getFear() {
        return fear;
    }

    public void setFear(Double fear) {
        this.fear = fear;
    }

    public Double getHappiness() {
        return happiness;
    }

    public void setHappiness(Double happiness) {
        this.happiness = happiness;
    }

    public Double getNeutral() {
        return neutral;
    }

    public void setNeutral(Double neutral) {
        this.neutral = neutral;
    }

    public Double getSadness() {
        return sadness;
    }

    public void setSadness(Double sadness) {
        this.sadness = sadness;
    }

    public Double getSurprise() {
        return surprise;
    }

    public void setSurprise(Double surprise) {
        this.surprise = surprise;
    }

    public void clearData(double except) {
        if (anger < except) anger = 0d;
        if (contempt < except) contempt = 0d;
        if (disgust < except) disgust = 0d;
        if (fear < except) fear = 0d;
        if (happiness < except) happiness = 0d;
        if (neutral < except) neutral = 0d;
        if (sadness < except) sadness = 0d;
        if (surprise < except) surprise = 0d;
    }



    public Map<EEmotion, Double> map() {
        Map<EEmotion, Double> map = new HashMap<>();
        map.put(EEmotion.ANGER, anger);
        map.put(EEmotion.CONTEMPT, contempt);
        map.put(EEmotion.DISGUST, disgust);
        map.put(EEmotion.FEAR, fear);
        map.put(EEmotion.HAPPINESS, happiness);
        map.put(EEmotion.NEUTRAL, neutral);
        map.put(EEmotion.SADNESS, sadness);
        map.put(EEmotion.SURPRISE, surprise);
        return map;
    }

    public EEmotion most() {
        Map<EEmotion, Double> map = map();
        EEmotion emotion = null;
        Double value = 0d;
        for (Map.Entry<EEmotion, Double> entry : map.entrySet()) {
            if (entry.getValue() > value) {
                value = entry.getValue();
                emotion = entry.getKey();
            }
        }
        return emotion;
    }

//    public static void main(String[] args) {
//        EmotionRecognizeScores emotionRecognizeScores = new EmotionRecognizeScores(0d, 0d, 6d, 5d, 4d, 3d, 2d, 1d);
//        emotionRecognizeScores.clearData(1);
//        System.out.println(emotionRecognizeScores.getEmotionExist());
//        return;
//    }
}
