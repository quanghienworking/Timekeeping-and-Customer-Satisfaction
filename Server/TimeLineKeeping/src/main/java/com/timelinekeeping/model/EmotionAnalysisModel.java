package com.timelinekeeping.model;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.EmotionCustomerEntity;
import com.timelinekeeping.modelAPI.EmotionRecognizeScores;

import java.io.Serializable;

/**
 * Created by TrungNN on 9/20/2016.
 */
public class EmotionAnalysisModel implements Serializable {
    private EEmotion emotionMost;
    private Double age;
    private Gender gender;
    private Double smile;
    private EmotionRecognizeScores emotion;

    public EmotionAnalysisModel() {
    }

    public EmotionAnalysisModel(EEmotion emotionMost, EmotionCustomerEntity emotionEntity) {
        this.emotionMost = emotionMost;
        if (emotionEntity != null) {
            this.age = emotionEntity.getAge();
            this.gender = emotionEntity.getGender();
            this.smile = emotionEntity.getSmile();
            Double anger = emotionEntity.getAnger();
            Double contempt = emotionEntity.getContempt();
            Double disgust = emotionEntity.getDisgust();
            Double fear = emotionEntity.getFear();
            Double happiness = emotionEntity.getHappiness();
            Double neutral = emotionEntity.getNeutral();
            Double sadness = emotionEntity.getSadness();
            Double surprise = emotionEntity.getSurprise();
            this.emotion = new EmotionRecognizeScores(anger, contempt, disgust, fear,
                    happiness, neutral, sadness, surprise);
        }
    }

    public EEmotion getEmotionMost() {
        return emotionMost;
    }

    public void setEmotionMost(EEmotion emotionMost) {
        this.emotionMost = emotionMost;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Double getSmile() {
        return smile;
    }

    public void setSmile(Double smile) {
        this.smile = smile;
    }

    public EmotionRecognizeScores getEmotion() {
        return emotion;
    }

    public void setEmotion(EmotionRecognizeScores emotion) {
        this.emotion = emotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmotionAnalysisModel that = (EmotionAnalysisModel) o;

        if (emotionMost != that.emotionMost) return false;
        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (gender != that.gender) return false;
        if (smile != null ? !smile.equals(that.smile) : that.smile != null) return false;
        return !(emotion != null ? !emotion.equals(that.emotion) : that.emotion != null);

    }

    @Override
    public int hashCode() {
        int result = emotionMost != null ? emotionMost.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (smile != null ? smile.hashCode() : 0);
        result = 31 * result + (emotion != null ? emotion.hashCode() : 0);
        return result;
    }
}