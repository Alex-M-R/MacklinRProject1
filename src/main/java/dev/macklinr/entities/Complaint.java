package dev.macklinr.entities;

import java.util.Objects;

public class Complaint
{
    int id;
    String description;
    Priority status;
    int meetingID;

    public Complaint() {
    }

    public Complaint(int id, String description, Priority status, int meetingID) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.meetingID = meetingID;
    }

    public Complaint(String description, Priority status, int meetingID) {
        this.description = description;
        this.status = status;
        this.meetingID = meetingID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getStatus() {
        return status;
    }

    public void setStatus(Priority status) {
        this.status = status;
    }

    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return id == complaint.id && meetingID == complaint.meetingID && Objects.equals(description, complaint.description) && status == complaint.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, status, meetingID);
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", meetingID=" + meetingID +
                '}';
    }
}
