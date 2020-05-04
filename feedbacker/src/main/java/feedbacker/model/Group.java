package feedbacker.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "groups")
@Getter @Setter
public class Group {

	@Column @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	private Exam exam;
	@Column
	private String name;
	@Column(name="tentative_grade")
	private String tentativeGrade;
	@Column
	private String feedback;
	
	@Override
	public int hashCode() {
		return Integer.parseInt(id.toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Group) {
			return id.equals(((Group) obj).getId());
		}
		return false;
	}
}
