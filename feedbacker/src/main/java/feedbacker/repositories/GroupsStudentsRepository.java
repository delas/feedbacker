package feedbacker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import feedbacker.model.Group;
import feedbacker.model.GroupStudents;

@Repository
public interface GroupsStudentsRepository extends JpaRepository<GroupStudents, Long> {
	
	List<GroupStudents> findByGroup(Group group);
}
