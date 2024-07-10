package com.innobytes.dao;

import java.util.List;

import com.innobytes.dto.QuizAttempt;

public interface QuizAttemptDao {
    void saveQuizAttempt(QuizAttempt attempt);
    List<QuizAttempt> getQuizAttemptsByUserId(Long userId);
}
