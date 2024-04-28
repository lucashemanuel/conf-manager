package com.lucashemanuel.confmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucashemanuel.confmanager.domain.checkin.Checkin;

public interface CheckinRepository extends JpaRepository<Checkin, Integer> {

  Optional<Checkin> findByAttendeeId(String attendeeId);
}
