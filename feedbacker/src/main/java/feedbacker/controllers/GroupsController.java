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

	@GetMapping("/feedback/{id}")
	public String get(Model model, @PathVariable(value = "id") long examId) {
		
		Optional<Exam> e = repoExams.findById(examId);
		if (e.isPresent()) {
			Map<Long, LinkedList<Student>> m = new HashMap<Long, LinkedList<Student>>();
			for (Group g : repoGroups.findByExam(e.get())) {
				m.put(g.getId(), new LinkedList<Student>());
				for (GroupStudents gs : repoGroupsStudent.findByGroup(g)) {
					m.get(g.getId()).add(gs.getStudent());
				}
			}
			model.addAttribute("groups", m);
		}
		return "pages/groups";
	}
}
