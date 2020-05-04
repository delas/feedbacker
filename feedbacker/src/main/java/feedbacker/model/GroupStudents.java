package feedbacker.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "groups_students")
@Getter @Setter
public class GroupStudents {

	@Column @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	private Student student;
	@ManyToOne(cascade = CascadeType.ALL)
	private Group group;
	@Column(name="tentative_grade")
	private String tentativeGrade;
	@Column
	private String feedback;
}
