package feedbacker.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import feedbacker.model.Group;
import feedbacker.model.GroupStudents;
import feedbacker.repositories.GroupsRepository;
import feedbacker.repositories.GroupsStudentsRepository;

@Controller
public class FeedbackController {

	@Autowired
	private GroupsRepository repoGroups;
	@Autowired
	private GroupsStudentsRepository repoGroupsStudents;

	@GetMapping("/feedback/group/{id}")
	public String get(Model model, @PathVariable(value = "id") long id) {

		Optional<Group> g = repoGroups.findById(id);
		if (g.isPresent()) {
			model.addAttribute("g", g.get());
		}
		List<GroupStudents> gss = repoGroupsStudents.findByGroup(g.get());
		List<GroupStudents> s1 = new LinkedList<GroupStudents>();
		List<GroupStudents> s2 = new LinkedList<GroupStudents>();
		for (int i = 0; i < gss.size(); i++) {
			if (i%2 == 0) {
				s1.add(gss.get(i));
			} else {
				s2.add(gss.get(i));
			}
		}
		model.addAttribute("students1", s1);
		model.addAttribute("students2", s2);

		return "pages/feedback";
	}
	
	@PostMapping("/feedback/update/{asset}/{type}/{id}")
	@ResponseBody
	public String edit(@PathVariable("asset") String asset, @PathVariable("type") String type, @PathVariable("id") long id, @RequestParam String value) {
		if ("individual".equals(asset)) {
			Optional<GroupStudents> ogs = repoGroupsStudents.findById(id);
			if (!ogs.isPresent()) {
				return "false";
			}
			if (type.equals("grade")) {
				GroupStudents gs = ogs.get();
				gs.setTentativeGrade(value);
				repoGroupsStudents.save(gs);
			} else if (type.equals("feedback")) {
				GroupStudents gs = ogs.get();
				gs.setFeedback(value);
				repoGroupsStudents.save(gs);
			}
			return "true";
		} else if ("group".equals(asset)) {
			Optional<Group> og = repoGroups.findById(id);
			if (!og.isPresent()) {
				return "false";
			}
			if (type.equals("grade")) {
				Group g = og.get();
				g.setTentativeGrade(value);
				repoGroups.save(g);
			} else if (type.equals("feedback")) {
				Group g = og.get();
				g.setFeedback(value);
				repoGroups.save(g);
			} else if (type.equals("name")) {
				Group g = og.get();
				g.setName(value);
				repoGroups.save(g);
			}
			return "true";
		}
		return "false";
	}
}
