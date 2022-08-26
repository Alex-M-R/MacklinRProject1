package dev.macklinr.services;

import dev.macklinr.entities.RequestState;
import dev.macklinr.entities.Speaker;

import java.util.List;

public interface SpeakerService
{
    boolean createSpeakerRelationship(int  meetingID, int userID);
    List<Speaker> getSpeakersForMeeting(int id);

    List<Speaker> getAllSpeakers();

    boolean updateSpeakerState(int id, RequestState state);
}
