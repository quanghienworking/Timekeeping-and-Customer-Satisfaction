package com.timelinekeeping.api;

import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.EStatusToDoTask;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.controller.PersonGroupControllerWeb;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.model.ToDoListModel;
import com.timelinekeeping.model.ToDoListModifyModel;
import com.timelinekeeping.service.serviceImplement.TodoListServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lethanhtan on 10/9/16.
 */

@RestController
@RequestMapping(I_URI.API_TODOLIST)
public class TodoListController {

    private Logger logger = Logger.getLogger(PersonGroupControllerWeb.class);

    @Autowired
    TodoListServiceImpl todoListService;

    // TO DO List
    @RequestMapping(value = I_URI.API_TODOLIST_GET, method = RequestMethod.GET)
    public BaseResponse get(@RequestParam(value = "month") String month,
                            @RequestParam(value = "year") String year,
                            @RequestParam(value = "day") String day,
                            @RequestParam(value = "accountId") String accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse baseResponse = new BaseResponse();
            List<ToDoListModel> list = todoListService.getToDoTask(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year), accountId);
            baseResponse.setData(list);
            baseResponse.setSuccess(true);
            return baseResponse;
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    
    @RequestMapping(value = I_URI.API_TODOLIST_SELECT_TASK, method = RequestMethod.GET)
    public BaseResponse selectTask(@RequestParam(value = "id") String taskId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse baseResponse = new BaseResponse();
            EStatusToDoTask statusToDoTask = todoListService.selectTask(Long.parseLong(taskId));
            baseResponse.setSuccess(true);
            baseResponse.setData(statusToDoTask.getIndex());
            return baseResponse;
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_CREATE, method = RequestMethod.POST)
    public BaseResponse create(@ModelAttribute ToDoListModifyModel modifyModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Pair<Boolean, String> result = todoListService.create(modifyModel);
            if (result.getKey()){
                return new BaseResponse(result.getKey(), new Pair<String, Long>("id", Long.valueOf(result.getValue())));
            }else {
                return new BaseResponse(result.getKey(), result.getValue());
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_UPDATE, method = RequestMethod.POST)
    public BaseResponse update(@ModelAttribute ToDoListModifyModel modifyModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Pair<Boolean, String> result = todoListService.update(modifyModel);
            if (result.getKey()){
                return new BaseResponse(result.getKey(), new Pair<String, Long>("id", Long.valueOf(result.getValue())));
            }else {
                return new BaseResponse(result.getKey(), result.getValue());
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_DELETE, method = RequestMethod.GET)
    public BaseResponse delete(@RequestParam("id") Long todoId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Pair<Boolean, String> result = todoListService.delete(todoId);
            if (result.getKey()){
                return new BaseResponse(result.getKey());
            }else {
                return new BaseResponse(result.getKey(), result.getValue());
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }
}
