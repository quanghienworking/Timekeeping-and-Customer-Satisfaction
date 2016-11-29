package com.timelinekeeping.util;

import com.timelinekeeping.model.EmotionAnalysisModel;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/26/2016.
 */
public class AnalyzeImage {

    private static final String fileResult = "D:\\Survey\\fileResult_11_29_001.csv";
    private static final String folder = "D:\\Survey\\Hien\\185Image\\500Image";

    public static void writeFile(Map<String, EmotionAnalysisModel> mapData) throws IOException {
        FileWriter fw = new FileWriter(fileResult);
        BufferedWriter bf = new BufferedWriter(fw);
        bf.write("NameFile,AGE_G,GENDER_G,Age,Gender,EmotionMost,Anger,Contempt,Disgust,Fear,Happiness,Neutral,Sadness,Surprise");
        bf.newLine();
        for (Map.Entry<String, EmotionAnalysisModel> map : mapData.entrySet()) {
            String nameFile = map.getKey();
            String[] split = nameFile.trim().split("[.-]+");
            String age_g = "", gender_g = "";
            if (split.length > 2) {
                age_g = split[0];
                gender_g = split[1];
            }
            EmotionAnalysisModel emotionAnalysisModel = map.getValue();
            bf.write(nameFile + ",");
            bf.write(age_g + ",");
            bf.write(gender_g + ",");

            if (emotionAnalysisModel != null) {
                bf.write(emotionAnalysisModel.getAge() + ",");
                bf.write(emotionAnalysisModel.getGender() + ",");
                bf.write(emotionAnalysisModel.getEmotionMost() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getAnger() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getContempt() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getDisgust() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getFear() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getHappiness() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getNeutral() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getSadness() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getSurprise() + "");
            }else {
                bf.write("Cannot detect Image" + ",");
            }
            bf.newLine();
        }
        bf.close();
        fw.close();
    }


    public static void main(String[] args) {

        try {
            File fileRoot = new File(folder);
            File[] imageList = fileRoot.listFiles();
            Map<String, EmotionAnalysisModel> mapResult = new HashMap<>();

            int i = 1;
            for (File imageFile : imageList) {
                try {
                    byte[] bytes = IOUtils.toByteArray(new FileInputStream(imageFile));
                    if (ValidateUtil.isImageFile(new FileInputStream(imageFile))) {
                        EmotionServiceImpl emotionService = new EmotionServiceImpl();
                        EmotionAnalysisModel response = emotionService.getCustomerEmotion(bytes);
                        if (response != null) {
                            mapResult.put(imageFile.getName(), response);
                        }else{
                            mapResult.put(imageFile.getName(), null);
                        }
                    }else {
                        mapResult.put(imageFile.getName(), null);
                    }
                } catch (Exception e) {
                    mapResult.put(imageFile.getName(), null);
                }
            }

            writeFile(mapResult);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}