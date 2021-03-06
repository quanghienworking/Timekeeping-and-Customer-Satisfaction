package com.timelinekeeping.api.mcs;

import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.accessAPI.PersonGroupServiceMCSImpl;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.modelMCS.FaceDetectResponse;
import com.timelinekeeping.modelMCS.PersonGroup;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lethanhtan on 9/9/16.
 */

@RestController
@RequestMapping("/api_mcs/face")
public class FaceController {

    private Logger logger = Logger.getLogger(FaceController.class);

    @Autowired
    private FaceServiceMCSImpl faceService;

    @Autowired
    private PersonGroupServiceMCSImpl personGroupsServiceMCS;

    @RequestMapping(value = {"/detect"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detect(@RequestParam("url") String urlImg) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse response = faceService.detect(urlImg);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    @RequestMapping(value = {"/detect_img"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detectimg(@RequestParam("img") MultipartFile img) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse response = null;
            if (ValidateUtil.isImageFile(img.getInputStream())) {
                response = faceService.detect(img.getInputStream());
            } else {
                response = new BaseResponse();
                response.setSuccess(false);
                response.setMessage("File not image format.");
            }
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = {"/identify"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse identify(@RequestParam("groupId") String groupId,
                                 @RequestParam("faceId") List<String> faceIds) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse response = faceService.identify(groupId, faceIds);
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = {"/identifyImage"}, method = RequestMethod.POST)
    @ResponseBody
    public List<BaseResponse> identifyImage(@RequestParam("image") MultipartFile image) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            // call function detect image
            BaseResponse detectFaceResponse = detectimg(image);
            List<FaceDetectResponse> faceDetectResponse = (ArrayList<FaceDetectResponse>) detectFaceResponse.getData();

            String faceId = faceDetectResponse.get(0).getFaceId();
            logger.info("FaceId: " + faceId);

            // call personGroup list
            BaseResponse personGroupListResponse = personGroupsServiceMCS.listAll(0, 1000);
            List<PersonGroup> personGroupList = (ArrayList<PersonGroup>) personGroupListResponse.getData();
            List<BaseResponse> identifyResponseList = new ArrayList<>();
            for (PersonGroup personGroup: personGroupList) {
                List<String> face = new ArrayList();
                face.add(faceId);
                BaseResponse identifyResponse = identify(personGroup.getPersonGroupId(), face);
                identifyResponseList.add(identifyResponse);
                logger.info("identifyResponse " + identifyResponse);
            }
            return identifyResponseList;

        } catch (Exception e) {
            logger.error(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }

        return null;
    }

}

