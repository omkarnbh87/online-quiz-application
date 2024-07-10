package com.innobytes.dto;

import java.util.List;

import lombok.Data;

@Data
public class Quiz {
    private Long id;
    private String title;
    private List<Question> questions;
}

