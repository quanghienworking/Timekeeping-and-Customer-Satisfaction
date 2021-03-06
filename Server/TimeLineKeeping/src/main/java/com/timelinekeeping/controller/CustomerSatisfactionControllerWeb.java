package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.constant.I_TIME;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.AccountCustomerServiceDetails;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.CustomerServiceReport;
import com.timelinekeeping.model.EmployeeReportCustomerService;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TrungNN on 10/5/2016.
 */
@Controller
@RequestMapping(I_URI.WEB_MANAGER_CUS_SATISFACTION)
public class CustomerSatisfactionControllerWeb {

    private Logger logger = Logger.getLogger(CustomerSatisfactionControllerWeb.class);

    @Autowired
    private EmotionServiceImpl emotionService;

    @RequestMapping(value = "/month/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadCustomerSatisfactionMonthView(Model model, HttpSession session) {
        logger.info("[Controller- Load Customer Satisfaction Month View] BEGIN");
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            // check is manager
            Long managerId = accountModel.getId();
            // get current date
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            Integer month = calendar.get(Calendar.MONTH) + 1;
            Integer year = calendar.get(Calendar.YEAR);
            Integer day = Integer.valueOf(IContanst.DEFAULT_INT);
            logger.info("[Controller- Load Customer Satisfaction Month View] current year: " + year);
            logger.info("[Controller- Load Customer Satisfaction Month View] current month: " + month);
            logger.info("[Controller- Load Customer Satisfaction Month View] current date: " + day);

            //get customer satisfaction report
            CustomerServiceReport customerServiceReport
                    = emotionService.reportCustomerService(year, month, day, managerId);
            if (customerServiceReport != null) {
                logger.info("[Controller- Load Customer Satisfaction Month View] "
                        + customerServiceReport.getDepartment().getName());
            } else {
                logger.info("[Controller- Load Customer Satisfaction Month View]  null");
            }

            // set side-bar
            String sideBar = IContanst.SIDE_BAR_MANAGER_CUSTOMER_SATISFACTION;

            model.addAttribute("CustomerServiceReport", customerServiceReport);
            model.addAttribute("SelectedDate", currentDate);
            // side-bar
            model.addAttribute("SideBar", sideBar);

            url = IViewConst.CUSTOMER_SATISFACTION_MONTH_VIEW;
        }

        logger.info("[Controller- Load Customer Satisfaction Month View] END");
        return url;
    }

    @RequestMapping(value = "/date/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadCustomerSatisfactionDateView(Model model, HttpSession session) {
        logger.info("[Controller- Load Customer Satisfaction Date View] BEGIN");
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            // check is manager
            Long managerId = accountModel.getId();
            // get current date
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            Integer month = calendar.get(Calendar.MONTH) + 1;
            Integer year = calendar.get(Calendar.YEAR);
            Integer day = calendar.get(Calendar.DAY_OF_MONTH);
            logger.info("[Controller- Load Customer Satisfaction Month View] current year: " + year);
            logger.info("[Controller- Load Customer Satisfaction Month View] current month: " + month);
            logger.info("[Controller- Load Customer Satisfaction Month View] current date: " + day);

            //get customer satisfaction report
            CustomerServiceReport customerServiceReport
                    = emotionService.reportCustomerService(year, month, day, managerId);
            if (customerServiceReport != null) {
                logger.info("[Controller- Load Customer Satisfaction Month View] "
                        + customerServiceReport.getDepartment().getName());
            } else {
                logger.info("[Controller- Load Customer Satisfaction Month View]  null");
            }

            // set side-bar
            String sideBar = IContanst.SIDE_BAR_MANAGER_CUSTOMER_SATISFACTION;

            model.addAttribute("CustomerServiceReport", customerServiceReport);
            model.addAttribute("SelectedDate", currentDate);
            // side-bar
            model.addAttribute("SideBar", sideBar);

            url = IViewConst.CUSTOMER_SATISFACTION_DATE_VIEW;
        }

        logger.info("[Controller- Load Customer Satisfaction Date View] END");
        return url;
    }

    //TODO
    @RequestMapping(value = "/month/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadCustomerSatisfactionDetailsView(@RequestParam("accountCustomerServiceDetails") String accountCustomerServiceDetailsJson,
                                                      Model model) {
        logger.info("[Controller- Load Customer Satisfaction Month Details View] BEGIN");
        logger.info("[Controller- Load Customer Satisfaction Month Details View] accountCustomerServiceDetailsJson: "
                + accountCustomerServiceDetailsJson);
        // parse string-json to object
        AccountCustomerServiceDetails accountCustomerServiceDetails
                = JsonUtil.convertObject(accountCustomerServiceDetailsJson, AccountCustomerServiceDetails.class, I_TIME.FULL_YEAR_MONTH);
        if (accountCustomerServiceDetails != null) {
            Long accountId = accountCustomerServiceDetails.getAccountId();
            Date selectedDate = accountCustomerServiceDetails.getSelectedDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            Integer month = calendar.get(Calendar.MONTH) + 1;
            Integer year = calendar.get(Calendar.YEAR);

            //get employee report customer service
            EmployeeReportCustomerService customerServiceReport
                    = emotionService.reportCustomerServiceEmployee(year, month, accountId);

            model.addAttribute("CustomerServiceReport", customerServiceReport);
            model.addAttribute("SelectedDate", selectedDate);
        }
        // set side-bar
        String sideBar = IContanst.SIDE_BAR_MANAGER_CUSTOMER_SATISFACTION;

        // side-bar
        model.addAttribute("SideBar", sideBar);

        logger.info("[Controller- Load Customer Satisfaction Month Details View] END");
        return IViewConst.CUSTOMER_SATISFACTION_MONTH_DETAILS_VIEW;
    }

    //TODO change method GET
    @RequestMapping(value = "/change_month", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String changeMonthCustomerSatisfactionView(@RequestParam("selectedMonth") String selectedMonth,
                                                      Model model, HttpSession session) {
        logger.info("[Controller- Change Month Customer Satisfaction Month View] BEGIN");
        logger.info("[Controller- Change Month Customer Satisfaction Month View] selected month: " + selectedMonth);
        // parse to date
        Date selectedDate = TimeUtil.parseToDate(selectedMonth, I_TIME.FULL_YEAR_MONTH);
        logger.info("[Controller- Change Month Customer Satisfaction Month View] selected date: " + selectedDate);
        // get month, year, day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);
        Integer day = Integer.valueOf(IContanst.DEFAULT_INT);
        logger.info("[Controller- Change Month Customer Satisfaction Month View] selected year: " + year);
        logger.info("[Controller- Change Month Customer Satisfaction Month View] selected month: " + month);
        logger.info("[Controller- Change Month Customer Satisfaction Month View] selected date: " + day);

        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            // check is manager
            Long managerId = accountModel.getId();

            //get customer satisfaction report
            CustomerServiceReport customerServiceReport
                    = emotionService.reportCustomerService(year, month, day, managerId);

            // set side-bar
            String sideBar = IContanst.SIDE_BAR_MANAGER_CUSTOMER_SATISFACTION;

            model.addAttribute("CustomerServiceReport", customerServiceReport);
            model.addAttribute("SelectedDate", selectedDate);
            // side-bar
            model.addAttribute("SideBar", sideBar);

            url = IViewConst.CUSTOMER_SATISFACTION_MONTH_VIEW;
        }

        logger.info("[Controller- Change Month Customer Satisfaction Month View] END");
        return url;
    }

    //TODO change method GET
    @RequestMapping(value = "/change_date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String changeDateCustomerSatisfactionView(@RequestParam("selectedMonth") String selectedMonth,
                                                     Model model, HttpSession session) {
        logger.info("[Controller- Change Date Customer Satisfaction Date View] BEGIN");
        logger.info("[Controller- Change Date Customer Satisfaction Date View] selected month: " + selectedMonth);
        // parse to date
        Date selectedDate = TimeUtil.parseToDate(selectedMonth, I_TIME.FULL_DATE);
        logger.info("[Controller- Change Date Customer Satisfaction Date View] selected date: " + selectedDate);
        // get month, year, day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        logger.info("[Controller- Change Date Customer Satisfaction Date View] selected year: " + year);
        logger.info("[Controller- Change Date Customer Satisfaction Date View] selected month: " + month);
        logger.info("[Controller- Change Date Customer Satisfaction Date View] selected date: " + day);

        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            // check is manager
            Long managerId = accountModel.getId();

            //get customer satisfaction report
            CustomerServiceReport customerServiceReport
                    = emotionService.reportCustomerService(year, month, day, managerId);

            // set side-bar
            String sideBar = IContanst.SIDE_BAR_MANAGER_CUSTOMER_SATISFACTION;

            model.addAttribute("CustomerServiceReport", customerServiceReport);
            model.addAttribute("SelectedDate", selectedDate);
            // side-bar
            model.addAttribute("SideBar", sideBar);

            url = IViewConst.CUSTOMER_SATISFACTION_DATE_VIEW;
        }

        logger.info("[Controller- Change Date Customer Satisfaction Date View] END");
        return url;
    }

    @RequestMapping(value = "/details/change_month", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String changeMonthDetailsCustomerSatisfactionView(@RequestParam("selectedMonth") String selectedMonth,
                                                             @RequestParam("accountId") Long accountId,
                                                             Model model) {
        logger.info("[Controller- Change Month Customer Satisfaction Details View] BEGIN");
        logger.info("[Controller- Change Month Customer Satisfaction Details View] selected month: " + selectedMonth);
        logger.info("[Controller- Change Month Customer Satisfaction Details View] accountId: " + accountId);
        // parse to date
        Date selectedDate = TimeUtil.parseToDate(selectedMonth, I_TIME.FULL_YEAR_MONTH);
        logger.info("[Controller- Change Month Customer Satisfaction View] selected date: " + selectedDate);
        // get month, year
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);
        logger.info("[Controller- Change Month Timekeeping View] selected month: " + month);
        logger.info("[Controller- Change Month Timekeeping View] selected year: " + year);

        //get employee report customer service
        EmployeeReportCustomerService customerServiceReport
                = emotionService.reportCustomerServiceEmployee(year, month, accountId);

        // set side-bar
        String sideBar = IContanst.SIDE_BAR_MANAGER_CUSTOMER_SATISFACTION;

        model.addAttribute("CustomerServiceReport", customerServiceReport);
        model.addAttribute("SelectedDate", selectedDate);
        // side-bar
        model.addAttribute("SideBar", sideBar);

        logger.info("[Controller- Change Month Customer Satisfaction Details View] END");
        return IViewConst.CUSTOMER_SATISFACTION_MONTH_DETAILS_VIEW;
    }
}
