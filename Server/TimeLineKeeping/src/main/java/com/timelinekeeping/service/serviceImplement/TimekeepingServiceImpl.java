package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.ETimeKeeping;
import com.timelinekeeping.constant.ETypeCheckin;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;
import com.timelinekeeping.model.AccountCheckInModel;
import com.timelinekeeping.model.CheckinManualModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.NotificationRepo;
import com.timelinekeeping.repository.TimekeepingRepo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/24/2016.
 */
@Service
public class TimekeepingServiceImpl {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TimekeepingRepo timekeepingRepo;

    private Logger logger = LogManager.getLogger(TimekeepingServiceImpl.class);

    public List<AccountCheckInModel> getEmployeeDepartment(Long departmentId, Long accountId){
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<AccountCheckInModel> listResult = new ArrayList<>();
//            List<AccountEntity> listAccount = accountRepo.findByDepartment(departmentId);
            List<AccountEntity> listAccount = accountRepo.findByDepartmentAndAccount(departmentId, accountId);
            for (AccountEntity accountEntity : listAccount) {
                TimeKeepingEntity timeKeepingEntity = timekeepingRepo.findByAccountCheckinDate(accountEntity.getId(), new Date());
                listResult.add(new AccountCheckInModel(accountEntity, timeKeepingEntity));
            }
            return listResult;
        }finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public List<CheckinManualModel> checkInManual(List<CheckinManualModel> listCheckin){

        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<CheckinManualModel> checkinManualModels = new ArrayList<>();

            for (CheckinManualModel checkinModel: listCheckin){
                Long accountId = checkinModel.getAccountId();
                AccountEntity accountEntity = accountRepo.findOne(accountId);
                if (accountEntity != null) {
                    TimeKeepingEntity timeKeeping = timekeepingRepo.findByAccountCheckinDate(accountId, new Date());
                    if (timeKeeping == null) {
                        timeKeeping = new TimeKeepingEntity();
                        timeKeeping.setAccount(accountEntity);
                        timeKeeping.setNote(checkinModel.getNote());
                        timeKeeping.setType(ETypeCheckin.CHECKIN_MANUAL);
                        timeKeeping.setStatus(ETimeKeeping.PRESENT);
                        timeKeeping.setTimeCheck(new Timestamp(new Date().getTime()));
                        timekeepingRepo.save(timeKeeping);
                        checkinModel.setSuccess(true);
                    }else {
                        checkinModel.setSuccess(false);
                        checkinModel.setMessage(String.format(ERROR.CHECK_IN_MANUAL_ACCOUNT_CHECKINED, accountId + ""));
                    }
                }else{
                    checkinModel.setSuccess(false);
                    checkinModel.setMessage(String.format(ERROR.CHECK_IN_MANUAL_NO_EXIST_ACCOUNTID, accountId + ""));
                }
                checkinManualModels.add(checkinModel);
            }
            timekeepingRepo.flush();
            return checkinManualModels;
        }finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }
}
