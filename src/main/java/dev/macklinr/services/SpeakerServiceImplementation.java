package dev.macklinr.services;

import dev.macklinr.daos.SpeakerDAO;
import dev.macklinr.entities.RequestState;
import dev.macklinr.entities.Speaker;

import java.util.List;

public class SpeakerServiceImplementation implements SpeakerService
{
    private final SpeakerDAO speakerDAO;
    public SpeakerServiceImplementation(SpeakerDAO speakerDAO) {
        this.speakerDAO = speakerDAO;
    }


    @Override
    public boolean createSpeakerRelationship(int  meetingID, int userID) {
        return this.speakerDAO.createSpeakerRequest(meetingID, userID);
    }

    @Override
    public List<Speaker> getSpeakersForMeeting(int id) {
        return this.speakerDAO.getAllSpeakersByMeetingId(id);
    }

    @Override
    public List<Speaker> getAllSpeakers() {
        return this.speakerDAO.getAllSpeakers();
    }

    @Override
    public boolean updateSpeakerState(int id, RequestState state) {
        return this.speakerDAO.updateSpeakerState(id, state);
    }
}