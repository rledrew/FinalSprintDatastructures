package org.example;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findAll(Sort sort);
}