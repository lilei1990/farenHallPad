package com.shyms.farendating.home.my_location.model;

import android.graphics.Bitmap;

/**
 * Created by hokas on 2015/7/29.
 */
public class BluetoothInfo {

    /**
     * id : 22224dd8da9a
     * areaName : 1
     * color : #FF0000
     * responseTimestampEpoch : 1438152964449
     * positionX : -30.87643757237386
     * positionY : 11.436578432868403
     * positionTimestampEpoch : 1438152963339
     * positionZ : 1
     * positionTimestamp : com.quuppa.a.J@46590e67
     * smoothedPositionZ : 1
     * smoothedPositionY : 11.436578432868403
     * smoothedPositionX : -30.87643757237386
     * positionAccuracy : 0.11217932437597981
     * areaId : 0002
     * version : 1.1
     */
    private String id;
    private String areaName;
    private String color;
    private long responseTimestampEpoch;
    private double positionX;
    private double positionY;
    private long positionTimestampEpoch;
    private int positionZ;
    private String positionTimestamp;
    private int smoothedPositionZ;
    private double smoothedPositionY;
    private double smoothedPositionX;
    private double positionAccuracy;
    private String areaId;
    private String version;
    private Bitmap bitmap;
    private float mapX;
    private float mapY;
    private DaoBanClick listener;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getResponseTimestampEpoch() {
        return responseTimestampEpoch;
    }

    public void setResponseTimestampEpoch(long responseTimestampEpoch) {
        this.responseTimestampEpoch = responseTimestampEpoch;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public long getPositionTimestampEpoch() {
        return positionTimestampEpoch;
    }

    public void setPositionTimestampEpoch(long positionTimestampEpoch) {
        this.positionTimestampEpoch = positionTimestampEpoch;
    }

    public int getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(int positionZ) {
        this.positionZ = positionZ;
    }

    public String getPositionTimestamp() {
        return positionTimestamp;
    }

    public void setPositionTimestamp(String positionTimestamp) {
        this.positionTimestamp = positionTimestamp;
    }

    public int getSmoothedPositionZ() {
        return smoothedPositionZ;
    }

    public void setSmoothedPositionZ(int smoothedPositionZ) {
        this.smoothedPositionZ = smoothedPositionZ;
    }

    public double getSmoothedPositionY() {
        return smoothedPositionY;
    }

    public void setSmoothedPositionY(double smoothedPositionY) {
        this.smoothedPositionY = smoothedPositionY;
    }

    public double getSmoothedPositionX() {
        return smoothedPositionX;
    }

    public void setSmoothedPositionX(double smoothedPositionX) {
        this.smoothedPositionX = smoothedPositionX;
    }

    public double getPositionAccuracy() {
        return positionAccuracy;
    }

    public void setPositionAccuracy(double positionAccuracy) {
        this.positionAccuracy = positionAccuracy;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getMapX() {
        return mapX;
    }

    public void setMapX(float mapX) {
        this.mapX = mapX;
    }

    public float getMapY() {
        return mapY;
    }

    public void setMapY(float mapY) {
        this.mapY = mapY;
    }

    public DaoBanClick getListener() {
        return listener;
    }

    public void setListener(DaoBanClick listener) {
        this.listener = listener;
    }



    public interface DaoBanClick {
        void onDaoBanClick(int x, int y);
    }

    @Override
    public String toString() {
        return "BluetoothInfo{" +
                "id='" + id + '\'' +
                ", areaName='" + areaName + '\'' +
                ", color='" + color + '\'' +
                ", responseTimestampEpoch=" + responseTimestampEpoch +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", positionTimestampEpoch=" + positionTimestampEpoch +
                ", positionZ=" + positionZ +
                ", positionTimestamp='" + positionTimestamp + '\'' +
                ", smoothedPositionZ=" + smoothedPositionZ +
                ", smoothedPositionY=" + smoothedPositionY +
                ", smoothedPositionX=" + smoothedPositionX +
                ", positionAccuracy=" + positionAccuracy +
                ", areaId='" + areaId + '\'' +
                ", version='" + version + '\'' +
                ", bitmap=" + bitmap +
                ", mapX=" + mapX +
                ", mapY=" + mapY +
                ", listener=" + listener +
                '}';
    }
}
