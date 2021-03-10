package com.smoothstack.utopia.api.dao;

import com.smoothstack.utopia.api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Long> {}
