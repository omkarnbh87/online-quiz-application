package com.innobytes.dto;

import java.util.List;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private List<String> options;
    private String correctAnswer;
}
