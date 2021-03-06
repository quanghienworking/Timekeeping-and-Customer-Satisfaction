package com.timelinekeeping.entity;

import com.timelinekeeping.constant.ETimeKeeping;
import com.timelinekeeping.constant.ETypeCheckin;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lethanhtan on 9/14/16.
 */

@Entity
@Table(name = "time_keeping")
public class TimeKeepingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "type", nullable = false)
    private ETypeCheckin type;

    @Basic
    @Column(name = "status", nullable = false)
    private ETimeKeeping status = ETimeKeeping.ABSENT;

    @Basic
    @Column(name = "time_check", nullable = false)
    private Timestamp timeCheck = new Timestamp(new Date().getTime());

    @Basic
    @Column(name = "note", length = Integer.MAX_VALUE)
    private String note;

    @Basic
    @Column(name = "image_path", length = 500)
    private String imagePath;

    @Basic
    @Column(name = "confidence")
    private Double confidence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @Basic
    @Column(name = "rp_manager_id")
    private Long rpManagerId;

    @Basic
    @Column(name = "rp_department_id")
    private Long rpDepartmentId;


    public TimeKeepingEntity() { }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimeCheck() {
        return timeCheck;
    }

    public void setTimeCheck(Timestamp timeCheck) {
        this.timeCheck = timeCheck;
    }

    public ETimeKeeping getStatus() {
        return status;
    }

    public void setStatus(ETimeKeeping status) {
        this.status = status;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public ETypeCheckin getType() {
        return type;
    }

    public void setType(ETypeCheckin type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getRpManagerId() {
        return rpManagerId;
    }

    public void setRpManagerId(Long rpManagerId) {
        this.rpManagerId = rpManagerId;
    }

    public Long getRpDepartmentId() {
        return rpDepartmentId;
    }

    public void setRpDepartmentId(Long rpDepartmentId) {
        this.rpDepartmentId = rpDepartmentId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}
