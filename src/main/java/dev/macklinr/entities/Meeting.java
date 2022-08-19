package dev.macklinr.entities;

import java.util.Objects;

public class Meeting
{
    int id;
    String address;
    long scheduledDate;
    String summary;

    public Meeting() {
    }

    public Meeting(int id, String address, long scheduledDate, String summary) {
        this.id = id;
        this.address = address;
        this.scheduledDate = scheduledDate;
        this.summary = summary;
    }

    public Meeting(String address, long scheduledDate, String summary) {
        this.address = address;
        this.scheduledDate = scheduledDate;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(long scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id && scheduledDate == meeting.scheduledDate && Objects.equals(address, meeting.address) && Objects.equals(summary, meeting.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, scheduledDate, summary);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", scheduledDate=" + scheduledDate +
                ", summary='" + summary + '\'' +
                '}';
    }
}
