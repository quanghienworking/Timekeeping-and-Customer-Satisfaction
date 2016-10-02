package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.entity.AccountEntity;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/26/2016.
 */
public class AccountTKReportModel {
    private Long id;
    private String fullname;
    private Integer dayCheckin;
    private Integer dayWork;
    private EStatus active;
    private Date dateDeactive;


    public AccountTKReportModel(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.fullname = entity.getFullname();
            this.active = entity.getActive();
            this.dateDeactive = entity.getTimeDeactive();
        }
    }

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayCheckin() {
        return dayCheckin;
    }

    public void setDayCheckin(Integer dayCheckin) {
        this.dayCheckin = dayCheckin;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getDayWork() {
        return dayWork;
    }

    public void setDayWork(Integer dayWork) {
        this.dayWork = dayWork;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public Date getDateDeactive() {
        return dateDeactive;
    }

    public void setDateDeactive(Date dateDeactive) {
        this.dateDeactive = dateDeactive;
    }
}
