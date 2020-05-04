package feedbacker.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import feedbacker.model.Exam;
import feedbacker.model.Group;
import feedbacker.model.GroupStudents;
import feedbacker.model.Student;
import feedbacker.repositories.ExamsRepository;
import feedbacker.repositories.GroupsRepository;
import feedbacker.repositories.GroupsStudentsRepository;

@Controller
public class GroupsController {

	@Autowired
	private ExamsRepository repoExams;
	@Autowired
	private GroupsRepository repoGroups;
	@Autowired
	private GroupsStudentsRepository repoGroupsStudent;

	@GetMapping("/feedback/exam/{id}")
	public String get(Model model, @PathVariable(value = "id") long examId) {
		
		Optional<Exam> e = repoExams.findById(examId);
		if (e.isPresent()) {
			Map<Group, LinkedList<Student>> m = new HashMap<Group, LinkedList<Student>>();
			for (Group g : repoGroups.findByExam(e.get())) {
				m.put(g, new LinkedList<Student>());
				for (GroupStudents gs : repoGroupsStudent.findByGroup(g)) {
					m.get(g).add(gs.getStudent());
				}
			}
			model.addAttribute("groups", m);
		}
		return "pages/groups";
	}
	
	@GetMapping("/feedback/exam/{id}/print")
	public String print(Model model, @PathVariable(value = "id") long examId) {
		Optional<Exam> e = repoExams.findById(examId);
		if (e.isPresent()) {
			model.addAttribute("exam", e.get());
			Map<Group, LinkedList<GroupStudents>> m = new HashMap<Group, LinkedList<GroupStudents>>();
			for (Group g : repoGroups.findByExam(e.get())) {
				m.put(g, new LinkedList<GroupStudents>());
				for (GroupStudents gs : repoGroupsStudent.findByGroup(g)) {
					m.get(g).add(gs);
				}
			}
			model.addAttribute("groups", m);
		}
		return "pages/print";
	}
}
