package com.lucashemanuel.confmanager.controllers;

import com.lucashemanuel.confmanager.dto.attendee.AttendeeBadgeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lucashemanuel.confmanager.services.AttendeeService;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
  private final AttendeeService attendeeService;

  @GetMapping("/{attendeeId}/badge")
  public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
    AttendeeBadgeResponseDTO response = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{attendeeId}/check-in")
  public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
    var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();
    this.attendeeService.checkInAttendee(attendeeId);
    return ResponseEntity.created(uri).build();
  }
}
