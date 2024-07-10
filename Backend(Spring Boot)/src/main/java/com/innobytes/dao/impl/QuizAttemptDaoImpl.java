package com.innobytes.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.innobytes.constants.ErrorCodeEnum;
import com.innobytes.dao.QuizAttemptDao;
import com.innobytes.dto.Quiz;
import com.innobytes.dto.QuizAttempt;
import com.innobytes.dto.User;
import com.innobytes.exception.QuizServiceException;

@Repository
public class QuizAttemptDaoImpl implements QuizAttemptDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void saveQuizAttempt(QuizAttempt attempt) {
		System.out.println("QuizAttemptDaoImpl.saveQuizAttempt invoked");

        String sql = "INSERT INTO quiz_attempts (user_id, quiz_id, score) VALUES (:userId, :quizId, :score)";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", attempt.getUserId());
        params.put("quizId", attempt.getQuizId());
        params.put("score", attempt.getScore());

        try {
        	jdbcTemplate.update(sql, params);
		} catch (Exception e) {
			System.out.println("exception:" + e);
			throw new QuizServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(),
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
		}
    }

    @Override
    public List<QuizAttempt> getQuizAttemptsByUserId(Long userId) {
		System.out.println("QuizAttemptDaoImpl.getQuizAttemptsByUserId invoked");

        String sql = "SELECT * FROM quiz_attempts WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        try {
        	return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
                QuizAttempt attempt = new QuizAttempt();
                attempt.setId(rs.getLong("id"));

                User user = new User();
                user.setId((int)rs.getLong("user_id"));
                attempt.setUserId(user.getId());

                Quiz quiz = new Quiz();
                quiz.setId(rs.getLong("quiz_id"));
                attempt.setQuizId(quiz.getId());

                attempt.setScore(rs.getInt("score"));
                return attempt;
            });
		} catch (Exception e) {
			System.out.println("exception:" + e);
			return new ArrayList<>();
		}
    }
}
