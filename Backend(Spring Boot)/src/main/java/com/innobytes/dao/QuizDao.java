package com.innobytes.dao;

import java.util.List;

import com.innobytes.dto.Quiz;

public interface QuizDao {
    void createQuiz(Quiz quiz);
    void updateQuiz(Quiz quiz);
    void deleteQuiz(Long quizId);
    Quiz getQuizById(Long quizId);
    List<Quiz> getAllQuizzes();
}
