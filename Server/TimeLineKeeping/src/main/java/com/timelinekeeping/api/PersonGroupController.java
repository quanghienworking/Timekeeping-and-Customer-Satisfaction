package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.controller.PersonGroupControllerWeb;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.PersonGroupsServiceMCS;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lethanhtan on 9/9/16.
 */

@RestController
@RequestMapping("/api/persongroups")
public class PersonGroupController {

    private Logger logger = Logger.getLogger(PersonGroupControllerWeb.class);

    @Autowired
    private PersonGroupsServiceMCS groupService;

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@RequestParam("groupId") String groupId,
                               @RequestParam("groupName") String groupName,
                               @RequestParam("descriptions") String descriptions) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + this.getClass().getEnclosingMethod().getName());
            if (StringUtils.isEmpty(groupName)) {
                groupName = groupId;
            }
            BaseResponse response = groupService.create(groupId, groupName, descriptions);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    @RequestMapping(value = {"/listAll"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse listAll(@RequestParam("start") int start,
                                @RequestParam("top") int top) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + this.getClass().getEnclosingMethod().getName());
            return groupService.listAll(start, top);
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    @RequestMapping(value = {"/training"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse trainPersonGroup(@RequestParam("person_groups_id") String persongroupid) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + this.getClass().getEnclosingMethod().getName());
            return groupService.trainGroup(persongroupid);
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    @RequestMapping(value = {"/training_status"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse trainPersonGroupStatus(@RequestParam("person_groups_id") String persongroupid) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + this.getClass().getEnclosingMethod().getName());
            return groupService.trainPersonGroupStatus(persongroupid);
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }
}
