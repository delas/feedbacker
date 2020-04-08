package feedbacker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter @Setter
public class Student {

	@Column @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String number;
	@Column
	private String name;
	@Column
	private String img;
}
