package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
public class ReminderModifyModel {

    private Long id;
    private String title;
    private String message;

    private Long time;
    private String location;
    private EStatus active = EStatus.ACTIVE;
    private Long managerId;
    private Long roomId;
    private Set<Long> employeeSet;

    public ReminderModifyModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Set<Long> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(String employeeString) {
        String[] listNumber = employeeString.split(",");
        List<Long> list = new ArrayList<>();
        for (String number : listNumber){
            list.add(Long.parseLong(number));
        }
        this.employeeSet = new HashSet<>(list);
    }

    public void setEmployeeSet(Set<Long> employeeSet) {
        this.employeeSet = employeeSet;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
