package dev.macklinr.services;

import dev.macklinr.daos.SpeakerDAO;
import dev.macklinr.entities.RequestState;
import dev.macklinr.entities.Speaker;

import java.util.List;
import java.util.stream.Collectors;

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
        return this.speakerDAO.getAllSpeakers().stream().filter(speaker -> speaker.getMeetingID() == id).collect(Collectors.toList());
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
