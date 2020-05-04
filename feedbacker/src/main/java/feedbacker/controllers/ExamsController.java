package feedbacker.controllers;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import feedbacker.model.Exam;
import feedbacker.model.Group;
import feedbacker.model.GroupStudents;
import feedbacker.model.Student;
import feedbacker.repositories.ExamsRepository;
import feedbacker.repositories.GroupsRepository;
import feedbacker.repositories.GroupsStudentsRepository;
import feedbacker.repositories.StudentsRepository;

@Controller
public class ExamsController {

	@Autowired
	private ExamsRepository repoExams;
	@Autowired
	private StudentsRepository repoStudents;
	@Autowired
	private GroupsRepository repoGroups;
	@Autowired
	private GroupsStudentsRepository repoGroupsStudents;

	@GetMapping("/")
	public String redirect() {
		return "redirect:/feedback";
	}
	
	@GetMapping("/feedback")
	public String get(Model model) {
		model.addAttribute("exams", repoExams.findAll());
		return "pages/exams";
	}
	
	@GetMapping("/feedback/exam/add")
	public String add(Exam exam, Model model) {
		return "pages/exams.add";
	}
	
	@PostMapping("/feedback/exam/add")
	public String add(@Valid Exam exam, @RequestParam("json") String JSon, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "pages/exams.add";
		}
		
		repoExams.save(exam);
		importer(JSon, exam);
		
		return "redirect:/feedback";
	}
	
	@GetMapping("/feedback/exam/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		Exam e = repoExams.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
		repoExams.delete(e);
		return "redirect:/feedback";
	}
	
	private void importer(String CampusNetJSON, Exam e) {
		System.out.println(CampusNetJSON);
		JSONArray obj = new JSONArray(CampusNetJSON);
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
	
}
