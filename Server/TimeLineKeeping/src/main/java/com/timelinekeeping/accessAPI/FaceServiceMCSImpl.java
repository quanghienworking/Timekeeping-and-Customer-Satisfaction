package com.timelinekeeping.accessAPI;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.modelAPI.FaceDetectRespone;
import com.timelinekeeping.modelAPI.FaceIdentifyConfidenceRespone;
import com.timelinekeeping.modelAPI.FaceIdentifyRequest;
import com.timelinekeeping.util.HTTPClientUtil;
import com.timelinekeeping.util.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
@Service
public class FaceServiceMCSImpl {

    private Logger logger = LogManager.getLogger(PersonGroupServiceMCSImpl.class);

    String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.cognitive.service.root.url");

    /**
     * detect commit
     */

    public BaseResponse detect(InputStream imgStream) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getName());
        String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.face.detech");
        String url = rootPath + urlAddition;

            /*** url*/
            URIBuilder builder = new URIBuilder(url)
                    .addParameter("returnFaceId", "true")
                    .addParameter("returnFaceLandmarks", "false")
                    .addParameter("returnFaceAttributes", "age,gender");

            /** entity*/
            byte[] bytes = IOUtils.toByteArray(imgStream);

            /** type Response JSON List*/

            /** Class return **/


            return HTTPClientUtil.getInstanceFace().toPostOct(builder.build(), new ByteArrayEntity(bytes), JsonUtil.LIST_PARSER, FaceDetectRespone.class);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public BaseResponse detect(String urlImg) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getName());
        String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.face.detech");
        String url = rootPath + urlAddition;

        /*** url*/
        URIBuilder builder = new URIBuilder(url)
                .addParameter("returnFaceId", "true")
                .addParameter("returnFaceLandmarks", "false")
                .addParameter("returnFaceAttributes", "age,gender");

        /** entity*/
        Map<String, String> mapEntity = new HashMap<>();
        mapEntity.put("url", urlImg);
        String jsonEntity = JsonUtil.toJson(mapEntity);

        return HTTPClientUtil.getInstanceFace().toPost(builder.build(), new StringEntity(jsonEntity, StandardCharsets.UTF_8), JsonUtil.LIST_PARSER, FaceDetectRespone.class);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    /**
     * identify face in group person has training
     *
     * @param groupId group of  person
     * @param faceIds list face need to identify
     * @return @{@link BaseResponse}
     * @apiNote https://dev.projectoxford.ai/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395239
     * <p>
     * url: https://api.projectoxford.ai/face/v1.0/identify
     * entity: (url)
     * typeJsonReturn @{@link JsonUtil}
     * return Class: @{@link List< FaceIdentifyConfidenceRespone >}
     * <p>
     * 9-11-2016
     * @author hientq
     */
    public BaseResponse identify(String groupId, List<String> faceIds) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getName());
            String urlIdentity = AppConfigKeys.getInstance().getApiPropertyValue("api.face.identity");
            String url = rootPath + urlIdentity;

            /*** url -> @{url}*/

            /** entity*/
            FaceIdentifyRequest identityRequest = new FaceIdentifyRequest(groupId, faceIds);
            String jsonEntity = JsonUtil.toJson(identityRequest);

            /** type Response @{{@link JsonUtil.LIST_PARSER}}*/

            /** Class return @{{@link FaceIdentifyConfidenceRespone}}**/


            return HTTPClientUtil.getInstanceFace().toPost(url, new StringEntity(jsonEntity, StandardCharsets.UTF_8),
                    JsonUtil.LIST_PARSER, FaceIdentifyConfidenceRespone.class);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }
}