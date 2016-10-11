package com.timelinekeeping.api;

import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.*;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/14/2016.
 */
@RestController
@RequestMapping(I_URI.API_ACCOUNT)
public class AccountController {

    Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private AccountServiceImpl accountService;

    @RequestMapping(value = I_URI.API_CREATE, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@ModelAttribute("account") AccountModifyModel account) {
        try {
            BaseResponseG<AccountModel> response = accountService.create(account);
            logger.info("AccountModel: " + JsonUtil.toJson(response));
            return response.toBaseResponse();
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }

    @RequestMapping(value = I_URI.API_LIST, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse search(@RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) Integer page,
                               @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) Integer size) {

        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Page<AccountModel> accountModelList = accountService.listAll(page, size);
            return new BaseResponse(true, accountModelList);
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_SEARCH_DEPARTMENT, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse searchByDepartment(@RequestParam(value = "departmentID") Long departmentID,
                                           @RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) Integer start,
                                           @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) Integer top) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("departmentID: " + departmentID);
            logger.info("start: " + start);
            logger.info("top: " + top);
            if (departmentID == null) {
                return new BaseResponse(false, ERROR.ACCOUNT_API_SEARCH_DEPARTMENT_EMPTY);
            }

            return new BaseResponse(true, accountService.searchByDepartment(departmentID, start, top));
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }


    @RequestMapping(value = I_URI.API_ACCOUNT_ADD_FACE, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addFaceToAccount(@RequestParam(value = "image") MultipartFile imageFile,
                                         @RequestParam(value = "accountId") Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return accountService.addFaceImg(Long.valueOf(accountId), imageFile.getInputStream());
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_CHECK_IN, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse checkin(@RequestParam(value = "image") MultipartFile fileImg) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return accountService.checkin(fileImg.getInputStream());
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_GET_REMINDER, method = RequestMethod.POST)
    public BaseResponse getReminderOnDay(@RequestParam(value = "accountId") String accountId) {
        try {
            BaseResponse baseResponse = new BaseResponse();
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<NotificationCheckInModel> list = accountService.getReminder(Long.parseLong(accountId));
            baseResponse.setSuccess(true);
            baseResponse.setData(list);
            return baseResponse;
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_UPDATE_TOKEN_ID_MOBILE, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateTokenMobile(@RequestParam(value = "accountID") String accountID,
                                          @RequestParam(value = "tokenID") String tokenID) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse baseResponse = new BaseResponse();
            boolean result = accountService.addMobileTokenID(accountID, tokenID);
            baseResponse.setSuccess(result);
            return baseResponse;
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_LOGIN, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse login(@RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password) {
        BaseResponse baseResponse = new BaseResponse();
        AccountModel accountModel = accountService.login(username, password);
        if (accountModel == null) {
            baseResponse.setSuccess(false);
            baseResponse.setMessage("Incorrect username or password");
        } else {
            baseResponse.setSuccess(true);
            baseResponse.setData(new Pair<>("token", accountModel.getToken()));
        }
        return baseResponse;
    }



}
