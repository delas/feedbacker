package feedbacker;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import feedbacker.model.Exam;
import feedbacker.model.Group;
import feedbacker.model.GroupStudents;
import feedbacker.model.Student;
import feedbacker.repositories.ExamsRepository;
import feedbacker.repositories.GroupsRepository;
import feedbacker.repositories.GroupsStudentsRepository;
import feedbacker.repositories.StudentsRepository;

//@Component
//@SpringBootApplication
public class Importer implements CommandLineRunner {
	
	@Autowired
    private ConfigurableApplicationContext context;
	
	@Autowired
	private StudentsRepository repoStudents;
	@Autowired
	private ExamsRepository repoExams;
	@Autowired
	private GroupsRepository repoGroups;
	@Autowired
	private GroupsStudentsRepository repoGroupsStudents;

//	@PostConstruct
	public void importer() {
		String courseName = "test";
		String input = "";

		Exam e = new Exam();
		e.setName(courseName);
		repoExams.save(e);
		
		JSONArray obj = new JSONArray(input);
		for(Object group : obj) {

			Group g = new Group();
			g.setExam(e);
			repoGroups.save(g);

			for (Object student : ((JSONObject) group).getJSONObject("author").getJSONArray("students")) {
				String stdName = ((JSONObject) student).getString("name");
				String stdNumber = ((JSONObject) student).getString("studentCode");
				String stdImg = ((JSONObject) student).getString("pictureUrl");
				
				Student s = repoStudents.findByNumber(stdNumber);
				if (s == null) {
					s = new Student();
					s.setName(stdName);
					s.setNumber(stdNumber);
					s.setImg(stdImg);
					repoStudents.save(s);
				}
				GroupStudents gs = new GroupStudents();
				gs.setGroup(g);
				gs.setStudent(s);
				repoGroupsStudents.save(gs);
			}
		}
	}
	
//	public static void main(String args[]) {
//		SpringApplication.run(Importer.class, args);
//	}

	@Override
	public void run(String... args) throws Exception {
//		 System.exit(SpringApplication.exit(context));
	}
}
