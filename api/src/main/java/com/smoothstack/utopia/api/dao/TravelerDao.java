package com.smoothstack.utopia.api.dao;

import com.smoothstack.utopia.api.model.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerDao extends JpaRepository<Traveler, Long> {}
