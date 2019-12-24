package com.sypron.courses.dao;

import com.sypron.courses.models.entities.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionDao extends JpaRepository<Action , Long> {
}
