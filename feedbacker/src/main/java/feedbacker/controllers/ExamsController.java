package feedbacker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import feedbacker.repositories.ExamsRepository;

@Controller
public class ExamsController {

	@Autowired
	private ExamsRepository repoExams;

	@GetMapping("/")
	public String get(Model model) {
		model.addAttribute("exams", repoExams.findAll());
		return "pages/exams";
	}
}
