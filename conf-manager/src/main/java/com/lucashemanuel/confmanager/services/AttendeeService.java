package com.lucashemanuel.confmanager.services;

import com.lucashemanuel.confmanager.domain.attendee.Attendee;
import com.lucashemanuel.confmanager.domain.attendee.exceptions.AttendeeNotFoundException;
import com.lucashemanuel.confmanager.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.lucashemanuel.confmanager.domain.checkin.CheckIn;
import com.lucashemanuel.confmanager.dto.attendee.AttendeeBadgeDTO;
import com.lucashemanuel.confmanager.dto.attendee.AttendeeBadgeResponseDTO;
import com.lucashemanuel.confmanager.dto.attendee.AttendeeDetails;
import com.lucashemanuel.confmanager.dto.attendee.AttendeesListResponseDTO;
import com.lucashemanuel.confmanager.repositories.AttendeeRepository;
import com.lucashemanuel.confmanager.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class AttendeeService {
  private final AttendeeRepository attendeeRepository;
  private final CheckInService checkInService;

  public List<Attendee> getAllAttendeesFromEvent(String eventId) {
    return this.attendeeRepository.findByEventId(eventId);
  }

  public AttendeesListResponseDTO getEventsAttendee(String eventId) {
    List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

    List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
      Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
      LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
      return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(),
          checkedInAt);
    }).toList();
    return new AttendeesListResponseDTO(attendeeDetailsList);
  }

  public void verifyAttendeeSubscription(String eventId, String email) {
    Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
    if (isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");
  }

  public Attendee registerAttendee(Attendee newAttendee) {
    this.attendeeRepository.save(newAttendee);
    return newAttendee;
  }

  public void checkInAttendee(String attendeeId) {
    Attendee attendee = this.getAttendee(attendeeId);
    this.checkInService.registerCheckIn(attendee);
  }

  private Attendee getAttendee(String attendeeId) {
    return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with id: " + attendeeId));
  }

  public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
    Attendee attendee = this.getAttendee(attendeeId);
    var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();
    AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());
    return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
  }
}
