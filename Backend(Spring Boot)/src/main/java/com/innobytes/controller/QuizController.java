package com.innobytes.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobytes.dto.Quiz;
import com.innobytes.dto.QuizAttempt;
import com.innobytes.service.QuizAttemptService;
import com.innobytes.service.QuizService;

@RestController
@RequestMapping("v1/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private QuizAttemptService quizAttemptService;

	@GetMapping("/all")
	public List<Quiz> getAllQuizzes() {
		System.out.println("QuizController.getAllQuizzes invoked");
		return quizService.getAllQuizzes();
	}

	@GetMapping("/{id}")
	public Quiz getQuizById(@PathVariable Long id) {
		System.out.println("QuizController.getQuizById invoked || id:" + id);
		return quizService.getQuizById(id);
	}

	@PostMapping("/{quizId}/attempt")
	public QuizAttempt attemptQuiz(@PathVariable Long quizId, @RequestBody List<String> answers, Principal principal) {
		System.out.println("QuizController.attemptQuiz invoked || quizId:" + quizId +" || answers:" + answers);
		String username = principal.getName();
		return quizAttemptService.attemptQuiz(quizId, username, answers);
	}

	@GetMapping("/attempts")
	public List<QuizAttempt> getUserQuizAttempts(Principal principal) {
		System.out.println("QuizController.getUserQuizAttempts invoked");
		String username = principal.getName();
		return quizAttemptService.getUserQuizAttempts(username);
	}
}
