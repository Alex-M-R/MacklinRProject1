package dev.macklinr.entities;

import java.util.Objects;

public class Speaker
{
    int id;

    int meetingID;

    int appUserID;


    RequestState state;

    String fName;

    String lName;

    String userName;

    Role appUserRole;

    String meetingSummary;

    public Speaker() {
    }

    public Speaker(int id, int meetingID, int appUserID, RequestState state, String fName, String lName, String userName, Role appUserRole, String meetingSummary) {
        this.id = id;
        this.meetingID = meetingID;
        this.appUserID = appUserID;
        this.state = state;
        this.fName = fName;
        this.lName = lName;
        this.userName = userName;
        this.appUserRole = appUserRole;
        this.meetingSummary = meetingSummary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    public int getAppUserID() {
        return appUserID;
    }

    public void setAppUserID(int appUserID) {
        this.appUserID = appUserID;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getAppUserRole() {
        return appUserRole;
    }

    public void setAppUserRole(Role appUserRole) {
        this.appUserRole = appUserRole;
    }

    public String getMeetingSummary() {
        return meetingSummary;
    }

    public void setMeetingSummary(String meetingSummary) {
        this.meetingSummary = meetingSummary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return id == speaker.id && meetingID == speaker.meetingID && appUserID == speaker.appUserID && state == speaker.state && Objects.equals(fName, speaker.fName) && Objects.equals(lName, speaker.lName) && Objects.equals(userName, speaker.userName) && appUserRole == speaker.appUserRole && Objects.equals(meetingSummary, speaker.meetingSummary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meetingID, appUserID, state, fName, lName, userName, appUserRole, meetingSummary);
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", meetingID=" + meetingID +
                ", appUserID=" + appUserID +
                ", state=" + state +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", userName='" + userName + '\'' +
                ", appUserRole=" + appUserRole +
                ", meetingSummary='" + meetingSummary + '\'' +
                '}';
    }
}
