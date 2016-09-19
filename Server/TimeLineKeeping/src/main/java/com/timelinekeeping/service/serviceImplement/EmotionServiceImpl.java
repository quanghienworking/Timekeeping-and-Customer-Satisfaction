package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.EmotionServiceMCSImpl;
import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.Emotion;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.modelAPI.EmotionRecognizeResponse;
import com.timelinekeeping.modelAPI.FaceDetectRespone;
import com.timelinekeeping.repository.EmotionRepo;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lethanhtan on 9/15/16.
 */
@Service
public class EmotionServiceImpl {

    private Logger logger = Logger.getLogger(EmotionServiceImpl.class);

    @Autowired
    private EmotionRepo repo;

    public BaseResponse save(InputStream inputStreamImg, Long employeeId, boolean isFirstTime) throws IOException, URISyntaxException {
        BaseResponse baseResponse = new BaseResponse();
        byte[] bytes = IOUtils.toByteArray(inputStreamImg);

        // emotion
        EmotionServiceMCSImpl emotionServiceMCS = new EmotionServiceMCSImpl();
        BaseResponse emotionResponse = emotionServiceMCS.recognize(new ByteArrayInputStream(bytes));

        // face detect
        FaceServiceMCSImpl faceServiceMCS = new FaceServiceMCSImpl();
        BaseResponse faceResponse = faceServiceMCS.detect(new ByteArrayInputStream(bytes));

        if (isFirstTime) {
            // add suggess
        }
        // TO-DO: implement multi thread
        // parser emotion response
        List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();
        EmotionRecognizeResponse emotionRecognize = emotionRecognizeList.get(0);

        // parser face response
        List<FaceDetectRespone> faceRecognizeList = (List<FaceDetectRespone>) faceResponse.getData();
        FaceDetectRespone faceDetectResponse = faceRecognizeList.get(0);

        // create time
        java.util.Date date= new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        Double anger = emotionRecognize.getScores().getAnger(); // get anger
        Double contempt = emotionRecognize.getScores().getContempt(); // get contempt
        Double disgust = emotionRecognize.getScores().getDisgust(); // get Disgust
        Double fear = emotionRecognize.getScores().getFear(); // get Fear
        Double happiness = emotionRecognize.getScores().getHappiness(); // get happiness
        Double neutral = emotionRecognize.getScores().getNeutral(); // get neutral
        Double sadness = emotionRecognize.getScores().getSadness(); // get sadness
        Double surprise = emotionRecognize.getScores().getSurprise(); // get surprise
        Double age = faceDetectResponse.getFaceAttributes().getAge();
        Gender gender = faceDetectResponse.getFaceAttributes().getGender().toUpperCase().equals("MALE") ? Gender.MALE : Gender.FEMALE;
        Double smile = faceDetectResponse.getFaceAttributes().getSmile();

        // save to database
        Emotion emotion = new Emotion(timestamp, employeeId, anger, contempt, disgust, fear, happiness, neutral, sadness, surprise, age, gender, smile);
        baseResponse.setData(repo.saveAndFlush(emotion));
        return baseResponse;
    }

    public BaseResponse analyseEmotion(Long id) {
        logger.info("[Analyse Emotion] BEGIN");
        BaseResponse baseResponse = new BaseResponse();
        Emotion emotion = repo.findOne(id);
        if (emotion != null) {
            logger.info("[Analyse Emotion] anger: " + emotion.getAnger());
            logger.info("[Analyse Emotion] contempt: " + emotion.getContempt());
            logger.info("[Analyse Emotion] disgust: " + emotion.getDisgust());
            logger.info("[Analyse Emotion] fear: " + emotion.getFear());
            logger.info("[Analyse Emotion] happiness: " + emotion.getHappiness());
            logger.info("[Analyse Emotion] neutral: " + emotion.getNeutral());
            logger.info("[Analyse Emotion] sadness: " + emotion.getSadness());
            logger.info("[Analyse Emotion] surprise: " + emotion.getSurprise());
            Map<String, Double> listEmotions = new HashMap<String, Double>();
            listEmotions.put("anger", emotion.getAnger());
            listEmotions.put("contempt", emotion.getContempt());
            listEmotions.put("disgust", emotion.getDisgust());
            listEmotions.put("fear", emotion.getFear());
            listEmotions.put("happiness", emotion.getHappiness());
            listEmotions.put("neutral", emotion.getNeutral());
            listEmotions.put("sadness", emotion.getSadness());
            listEmotions.put("surprise", emotion.getSurprise());
            Map.Entry<String, Double> maxEntry = null;
            for (Map.Entry<String, Double> entry : listEmotions.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            logger.info("[Analyse Emotion] current emotion: " + maxEntry.getKey());
            logger.info("[Analyse Emotion] ratio: " + maxEntry.getValue());
            baseResponse.setData(maxEntry);
        }
        logger.info("[Analyse Emotion] END");
        return baseResponse;
    }
}
