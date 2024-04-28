package com.lucashemanuel.confmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucashemanuel.confmanager.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, String> {

}
