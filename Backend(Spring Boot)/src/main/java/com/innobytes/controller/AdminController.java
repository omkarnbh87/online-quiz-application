package com.innobytes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobytes.dto.Quiz;
import com.innobytes.service.QuizService;

@RestController
@RequestMapping("v1/admin")
public class AdminController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/quiz")
    public void createQuiz(@RequestBody Quiz quiz) {
    	System.out.println("QuizService.createQuiz invoked || quiz:" + quiz);
        quizService.createQuiz(quiz);
    }

    @PutMapping("/quiz")
    public void updateQuiz(@RequestBody Quiz quiz) {
    	System.out.println("QuizService.updateQuiz invoked || quiz:" + quiz);
        quizService.updateQuiz(quiz);
    }

    @DeleteMapping("/quiz/{id}")
    public void deleteQuiz(@PathVariable Long id) {
    	System.out.println("QuizService.deleteQuiz invoked || id:" + id);
        quizService.deleteQuiz(id);
    }

    @GetMapping("/quiz/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
    	System.out.println("QuizService.getQuizById invoked || id:" + id);
        return quizService.getQuizById(id);
    }

    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes() {
    	System.out.println("QuizService.getAllQuizzes invoked");
        return quizService.getAllQuizzes();
    }
}
