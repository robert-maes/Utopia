package com.smoothstack.utopia.api.dao;

import com.smoothstack.utopia.api.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatDao extends JpaRepository<Seat, Long> {}
