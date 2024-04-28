package com.lucashemanuel.confmanager.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lucashemanuel.confmanager.domain.attendee.Attendee;
import com.lucashemanuel.confmanager.domain.checkin.Checkin;
import com.lucashemanuel.confmanager.dto.attendee.AttendeeDetails;
import com.lucashemanuel.confmanager.dto.attendee.AttendeesListResponseDTO;
import com.lucashemanuel.confmanager.repositories.AttendeeRepository;
import com.lucashemanuel.confmanager.repositories.CheckinRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendeeService {
  private final AttendeeRepository attendeeRepository;
  private final CheckinRepository checkinRepository;

  public List<Attendee> getAllAttendeesFromEvent(String eventId) {
    return this.attendeeRepository.findByEventId(eventId);
  }

  public AttendeesListResponseDTO getEventsAttendee(String eventId) {
    List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);
    List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
      Optional<Checkin> checkIn = this.checkinRepository.findByAttendeeId(attendee.getId());
      LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
      return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(),
          checkedInAt);
    }).toList();
    return new AttendeesListResponseDTO(attendeeDetailsList);
  }
}
