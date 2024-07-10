package com.innobytes.dto;

import lombok.Data;

@Data
public class QuizAttempt {
    private Long id;
    private Long userId;
    private Long quizId;
    private int score;
}