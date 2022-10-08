package com.sai.linq.wearable.models;

import com.google.gson.annotations.SerializedName;
import com.microsoft.azure.storage.table.TableServiceEntity;

import java.io.Serializable;

/**
 * Created by craigknee on 8/8/17.
 */

public class AlertItemTableServiceEntity extends TableServiceEntity implements Serializable{

    /*
     * Partition Key
     * Designates a particular facility. Combination of 2 char state id, 2 char type of install, 3 digit facility id
     * CAVN002 - California based Ventilator installation with facility id of 002
     */

    /*
     * RowKey
     * Combination of Building Id, Floor ID, Room #, and device type
     * Example: BUILD1_FLOOR2_605B_VENT
     */

    private String BuildingID;

    private String FloorID;

    private String RoomID;

    private String DeviceType;

    /**
     * Indicates last CMS to update the row.  Derived from HospitalID and a single letter to designate the particular CMS.
     * CAVN002D would be "D" CMS at the facility CAVN002
     */
    private String CMS;

    /**
     * Time state changed for that transmitter. Used by Python CMS program to determine which state change is valid for
     * that device and to determine when an alarm began.
     * Format 2017-06-21 13:02
     */
    private String LocalTime;

    /**
     * State of Device:
     * I = In Use
     * N = Not in Use
     * A = Alarm
     * D = Disconnected (When previously in use)
     */
    private String State;

    /**
     * Serial number of a particular transmitter. CMS program associates serial nuber with room number as part of its
     * configuration settings
     */
    @SerializedName("DeviceSerial")
    private String DeviceSerial;

    /**
     * Timestamp created by DB when row is created/updated in the cloud.
     */
    @SerializedName("Timestamp")
    private String rowTimeStamp;

    // Note: An entity's partition and row key uniquely identify the entity in
    // the table.
    // Entities with the same partition key can be queried faster than those
    // with different partition keys.
//    public AlertItemTableServiceEntity(String lastName, String firstName) {
//        this.partitionKey = lastName;
//        this.rowKey = firstName;
//    }


    public AlertItemTableServiceEntity() {}

    public String getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(String buildingID) {
        BuildingID = buildingID;
    }

    public String getFloorID() {
        return FloorID;
    }

    public void setFloorID(String floorID) {
        FloorID = floorID;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getCMS() {
        return CMS;
    }

    public void setCMS(String CMS) {
        this.CMS = CMS;
    }

    public String getLocalTime() {
        return LocalTime;
    }

    public void setLocalTime(String localTime) {
        LocalTime = localTime;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getDeviceSerial() {
        return DeviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        DeviceSerial = deviceSerial;
    }

    public String getRowTimeStamp() {
        return rowTimeStamp;
    }

    public void setRowTimeStamp(String rowTimeStamp) {
        this.rowTimeStamp = rowTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlertItemTableServiceEntity that = (AlertItemTableServiceEntity) o;

//        if (!getRowKey().equals(that.getRowKey())) return false;
//        return getTimestamp().equals(that.getTimestamp());
        return getRowKey().equals(that.getRowKey());

    }

    @Override
    public int hashCode() {
        int result = getRowKey().hashCode();
        result = 31 * result; //+ getTimestamp().hashCode();
        return result;
    }
}
