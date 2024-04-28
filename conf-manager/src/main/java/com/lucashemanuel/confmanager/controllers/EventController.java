package com.lucashemanuel.confmanager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.lucashemanuel.confmanager.services.EventService;
import com.lucashemanuel.confmanager.services.AttendeeService;
import com.lucashemanuel.confmanager.dto.attendee.AttendeesListResponseDTO;
import com.lucashemanuel.confmanager.dto.event.EventIdDTO;
import com.lucashemanuel.confmanager.dto.event.EventRequestDTO;
import com.lucashemanuel.confmanager.dto.event.EventResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;
  private final AttendeeService attendeeService;

  @GetMapping("/{id}")
  public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {

    EventResponseDTO event = this.eventService.getEventDetail(id);
    return ResponseEntity.ok(event);
  }

  @PostMapping
  public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body,
      UriComponentsBuilder uriComponentsBuilder) {
    EventIdDTO eventIdDTO = this.eventService.createEvent(body);

    var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
    return ResponseEntity.created(uri).body(eventIdDTO);
  }

  @GetMapping("/attendees/{id}")
  public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {

    AttendeesListResponseDTO attendeesListResponse = this.attendeeService.getEventsAttendee(id);
    return ResponseEntity.ok(attendeesListResponse);
  }
}
