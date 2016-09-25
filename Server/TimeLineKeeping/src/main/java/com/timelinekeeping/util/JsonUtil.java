package com.timelinekeeping.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timelinekeeping.model.CheckinManualRequestModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/8/2016.
 */
public class JsonUtil {

    public static final int NORMAl_PARSER = 0;
    public static final int TIME_PARSER = 1;
    public static final int LIST_PARSER = 2;
    public static final int MAP_PARSER = 3;


    private static ObjectMapper mapper = new ObjectMapper();
    private static Logger logger = LogManager.getLogger(JsonUtil.class);

    public static String toJson(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static <T> T convertObject(String data, Class<T> classObj) {
        try {
            return (T) mapper.readValue(data, classObj);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static <T> T convertObject(String data, Class<T> classObj, String formatTime) {
        try {
            DateFormat format = new SimpleDateFormat(formatTime);
            mapper.setDateFormat(format);
            return mapper.readValue(data, classObj);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> convertListObject(String data, Class<T> classObj) {
        try {
            return mapper.readValue(data, mapper.getTypeFactory().constructCollectionType(List.class, classObj));
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> convertMapObject(String data, Class<T> classObj) {
        try {
            return mapper.readValue(data, mapper.getTypeFactory().constructMapType(Map.class, String.class, classObj));
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static void main(String[] args) {
//        String mapString ="{\"group\": \"trưởng ban đào tạo\", \"description\": \"Chức vị chuyên môn quản lý đào tạo cho trường\"}";
//
//        Map<String, String> map = convertMapObject(mapString, String.class);
//
//        System.out.println(toJson(map));

        String mapString = "[{\"accountId\":\"1\", \"statusCheckin\":\"false\", \"note\":\"trung 1\"},{\"accountId\":\"3\", \"statusCheckin\":\"true\", \"note\":\"\"},{\"accountId\":\"4\", \"statusCheckin\":\"false\", \"note\":\"too late\"}]";
        List<CheckinManualRequestModel> list = convertListObject(mapString, CheckinManualRequestModel.class);
        if (list != null && list.size() > 0) {
            System.out.println("result: " + list.get(0).getNote());
        }
    }
}
