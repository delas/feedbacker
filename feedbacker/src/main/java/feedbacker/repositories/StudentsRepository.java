package feedbacker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import feedbacker.model.Student;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Long> {
	
	Student findByNumber(String number);
}
