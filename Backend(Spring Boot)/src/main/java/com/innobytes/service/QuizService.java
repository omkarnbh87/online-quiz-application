package com.innobytes.service;

import java.util.List;

import com.innobytes.dto.Quiz;

public interface QuizService {
    void createQuiz(Quiz quiz);
    void updateQuiz(Quiz quiz);
    void deleteQuiz(Long quizId);
    Quiz getQuizById(Long quizId);
    List<Quiz> getAllQuizzes();
}
