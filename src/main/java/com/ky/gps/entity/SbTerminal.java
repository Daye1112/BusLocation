package com.ky.gps.entity;

public class SbTerminal {

    private String gpsId;
    private SbBusPosition sbBusPosition;
    private String agreement;

    public SbTerminal() {
    }

    public SbTerminal(String gpsId, SbBusPosition sbBusPosition, String agreement) {
        super();
        this.gpsId = gpsId;
        this.sbBusPosition = sbBusPosition;
        this.agreement = agreement;
    }

    public String getGpsId() {
        return gpsId;
    }

    public void setGpsId(String gpsId) {
        this.gpsId = gpsId;
    }

    public SbBusPosition getSbBusPosition() {
        return sbBusPosition;
    }

    public void setSbBusPosition(SbBusPosition sbBusPosition) {
        this.sbBusPosition = sbBusPosition;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    @Override
    public String toString() {
        return "SbTerminal [gpsId=" + gpsId + ", sbBusPosition=" + sbBusPosition + ", agreement=" + agreement + "]";
    }

}
