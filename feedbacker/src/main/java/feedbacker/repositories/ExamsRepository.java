package feedbacker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import feedbacker.model.Exam;

@Repository
public interface ExamsRepository extends JpaRepository<Exam, Long> { }
