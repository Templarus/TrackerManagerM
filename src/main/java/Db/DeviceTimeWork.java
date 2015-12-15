/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Db;

import java.sql.Time;
import java.sql.Date;

/**
 * 
 * @author Asus
 */
public class DeviceTimeWork {
    
    private String deviceId;
    private Date dtWork;
    private Time timeBegin;
    private Time timeEnd;

    public DeviceTimeWork(String deviceId, Date dtWork, Time timeBegin,Time timeEnd) {
        this.deviceId = deviceId;
        this.dtWork = dtWork;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
                
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getDtWork() {
        return dtWork;
    }

    public void setDtWork(Date dtWork) {
        this.dtWork = dtWork;
    }

    public Time getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Time timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }
    
    
    
    
}
