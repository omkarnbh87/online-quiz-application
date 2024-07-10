package com.innobytes.service;

import java.util.List;

import com.innobytes.dto.QuizAttempt;

public interface QuizAttemptService {
    QuizAttempt attemptQuiz(Long quizId, String username, List<String> answers);
    List<QuizAttempt> getUserQuizAttempts(String username);
}
