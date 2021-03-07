package com.smoothstack.utopia.api.seat_type;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {
  Optional<SeatType> findSeatTypeByClassification(String classification);
}
