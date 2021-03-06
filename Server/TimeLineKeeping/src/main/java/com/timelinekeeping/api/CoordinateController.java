package com.timelinekeeping.api;

import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.CoordinateModel;
import com.timelinekeeping.service.serviceImplement.CoordinateServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lethanhtan on 10/20/16.
 */

@RestController
@RequestMapping(I_URI.API_COORDINATE)
public class CoordinateController {

    Logger logger = LogManager.getLogger(CoordinateController.class);

    @Autowired
    private CoordinateServiceImpl coordinateService;

    @RequestMapping(value = I_URI.API_COORDINATE_GET_ROOM_POINT, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getRoomPoint() {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            List<CoordinateModel> listRoom = coordinateService.getRoomPoint();
            if (listRoom != null) {
                return new BaseResponse(true, listRoom);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }

    }

    @RequestMapping(value = I_URI.API_COORDINATE_GET_POINT, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getPoint() {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            List<CoordinateModel> listRoom = coordinateService.getPoint();
            if (listRoom != null) {
                return new BaseResponse(true, listRoom);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }
}
