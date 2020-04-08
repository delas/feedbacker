package feedbacker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import feedbacker.model.Exam;
import feedbacker.model.Group;

@Repository
public interface GroupsRepository extends JpaRepository<Group, Long> {
	
	List<Group> findByExam(Exam exam);
}
