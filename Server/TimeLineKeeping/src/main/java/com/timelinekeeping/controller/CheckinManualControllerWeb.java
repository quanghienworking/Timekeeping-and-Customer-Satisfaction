package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.model.AccountCheckInModel;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.CheckinManualModel;
import com.timelinekeeping.model.CheckinManualRequestModel;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TrungNN on 9/24/2016.
 */
@Controller
@RequestMapping("/manager/check_in")
public class CheckinManualControllerWeb {

    private Logger logger = Logger.getLogger(CheckinManualControllerWeb.class);

    @Autowired
    private TimekeepingServiceImpl timekeepingService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadCheckinManualView(Model model, HttpSession session) {
        logger.info("[Controller- Load Check-in Manual View] BEGIN");
        String url = IViewConst.INVALID_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            Long accountId = accountModel.getId();
            // get employees list in the department
            List<AccountCheckInModel> accountCheckInModels
                    = timekeepingService.getEmployeeUnderManager(accountId);
            if (accountCheckInModels != null) {
                int sizeOfListAccounts = accountCheckInModels.size();
                model.addAttribute("SizeOfListAccounts", sizeOfListAccounts);
            }

            // get current date
            Date currentDate = new Date();

            model.addAttribute("AccountCheckInModels", accountCheckInModels);
            model.addAttribute("CurrentDate", currentDate);
            url = IViewConst.CHECK_IN_MANUAL_VIEW;
        }

        logger.info("[Controller- Load Check-in Manual View] END");
        return url;
    }

    @RequestMapping(value = "/checkinManualProcessing", method = RequestMethod.POST)
    public String checkinManual(@RequestParam("accountCheckInModels") String accountCheckInModels) {
        logger.info("[Controller- Check-in Manual] BEGIN");
        logger.info("[Controller- Check-in Manual] accountCheckInModels: " + accountCheckInModels);
        // parse string-json to object
        List<CheckinManualRequestModel> checkinManualRequestModels
                = JsonUtil.convertListObject(accountCheckInModels, CheckinManualRequestModel.class);
        if (checkinManualRequestModels != null && checkinManualRequestModels.size() > 0) {
            logger.info("[Controller- Check-in Manual] size of accountCheckInModels: " + checkinManualRequestModels.size());
            List<CheckinManualModel> listCheckIn = new ArrayList<>();

            for (CheckinManualRequestModel checkinManualRequestModel : checkinManualRequestModels) {
                Long accountId = checkinManualRequestModel.getAccountId();
                String note = checkinManualRequestModel.getNote();
                boolean isChecked = checkinManualRequestModel.isStatusCheckin();
                logger.info("[Controller- Check-in Manual] id: " + accountId);
                logger.info("[Controller- Check-in Manual] statusCheckin: " + isChecked);
                logger.info("[Controller- Check-in Manual] note: " + note);

                if (isChecked) {
                    listCheckIn.add(new CheckinManualModel(accountId, note));
                }
            }
            logger.info("[Controller- Check-in Manual] size of list check-in: " + listCheckIn.size());

            List<CheckinManualModel> checkinManualModels = timekeepingService.checkInManual(listCheckIn);
            // TODO: check result check-in manual
        }

        logger.info("[Controller- Check-in Manual] END");
        return "redirect:/manager/check_in/";
    }
}
