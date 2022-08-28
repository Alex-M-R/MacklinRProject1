package dev.macklinr.daos;

import dev.macklinr.entities.Meeting;
import dev.macklinr.entities.RequestState;
import dev.macklinr.entities.Speaker;
import dev.macklinr.entities.User;

import java.util.List;

public interface SpeakerDAO
{
    boolean createSpeakerRequest(int  meetingID, int userID);

    List<Speaker> getAllSpeakers();

    boolean updateSpeakerState(int id, RequestState state);
}
