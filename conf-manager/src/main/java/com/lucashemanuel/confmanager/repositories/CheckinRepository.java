package com.lucashemanuel.confmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucashemanuel.confmanager.domain.checkin.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {

  Optional<CheckIn> findByAttendeeId(String attendeeId);
}
