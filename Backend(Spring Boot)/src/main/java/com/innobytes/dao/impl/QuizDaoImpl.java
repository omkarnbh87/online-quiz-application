package com.innobytes.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.innobytes.constants.ErrorCodeEnum;
import com.innobytes.dao.QuizDao;
import com.innobytes.dto.Question;
import com.innobytes.dto.Quiz;
import com.innobytes.exception.QuizServiceException;

@Repository
public class QuizDaoImpl implements QuizDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void createQuiz(Quiz quiz) {
		System.out.println("QuizDaoImpl.createQuiz invoked");

		String quizSql = "INSERT INTO quizzes (title) VALUES (:title)";
		Map<String, Object> params = new HashMap<>();
		params.put("title", quiz.getTitle());

		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(quizSql, new MapSqlParameterSource(params), keyHolder);

			// Retrieve the generated quiz ID
			Long quizId = keyHolder.getKey().longValue();

			for (Question question : quiz.getQuestions()) {
				String questionSql = "INSERT INTO questions (quiz_id, title, correct_answer) VALUES (:quizId, :title, :correctAnswer)";
				Map<String, Object> questionParams = new HashMap<>();
				questionParams.put("quizId", quizId);
				questionParams.put("title", question.getTitle());
				questionParams.put("correctAnswer", question.getCorrectAnswer());

				KeyHolder questionKeyHolder = new GeneratedKeyHolder();
				jdbcTemplate.update(questionSql, new MapSqlParameterSource(questionParams), questionKeyHolder);

				Long questionId = questionKeyHolder.getKey().longValue();

				for (String option : question.getOptions()) {
					String optionSql = "INSERT INTO options (question_id, option_text) VALUES (:questionId, :optionText)";
					Map<String, Object> optionParams = new HashMap<>();
					optionParams.put("questionId", questionId);
					optionParams.put("optionText", option);

					jdbcTemplate.update(optionSql, new MapSqlParameterSource(optionParams));
				}
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
			throw new QuizServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(), ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
		}
	}

	@Override
	public void updateQuiz(Quiz quiz) {
	    System.out.println("QuizDaoImpl.updateQuiz invoked");

	    String quizSql = "UPDATE quizzes SET title = :title WHERE id = :id";
	    Map<String, Object> quizParams = new HashMap<>();
	    quizParams.put("title", quiz.getTitle());
	    quizParams.put("id", quiz.getId());

	    try {
	        jdbcTemplate.update(quizSql, quizParams);

	        // Update questions and options
	        for (Question question : quiz.getQuestions()) {
	            String questionSql = "UPDATE questions SET title = :title, correct_answer = :correctAnswer WHERE id = :id";
	            Map<String, Object> questionParams = new HashMap<>();
	            questionParams.put("title", question.getTitle());
	            questionParams.put("correctAnswer", question.getCorrectAnswer());
	            questionParams.put("id", question.getId());

	            jdbcTemplate.update(questionSql, questionParams);

	            // Delete existing options for the question
	            String deleteOptionsSql = "DELETE FROM options WHERE question_id = :questionId";
	            Map<String, Object> deleteOptionsParams = new HashMap<>();
	            deleteOptionsParams.put("questionId", question.getId());
	            jdbcTemplate.update(deleteOptionsSql, deleteOptionsParams);

	            // Insert new options for the question
	            for (String option : question.getOptions()) {
	                String insertOptionSql = "INSERT INTO options (question_id, option_text) VALUES (:questionId, :optionText)";
	                Map<String, Object> optionParams = new HashMap<>();
	                optionParams.put("questionId", question.getId());
	                optionParams.put("optionText", option);

	                jdbcTemplate.update(insertOptionSql, optionParams);
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Exception: " + e.getMessage());
	        throw new QuizServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
	                ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(), ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
	    }
	}


	@Override
	public void deleteQuiz(Long quizId) {
		System.out.println("QuizDaoImpl.deleteQuiz invoked");

		String deleteAttemptsSql = "DELETE FROM quiz_attempts WHERE quiz_id = :quizId";
		String deleteOptionsSql = "DELETE FROM options WHERE question_id IN (SELECT id FROM questions WHERE quiz_id = :quizId)";
		String deleteQuestionsSql = "DELETE FROM questions WHERE quiz_id = :quizId";
		String deleteQuizSql = "DELETE FROM quizzes WHERE id = :quizId";
		Map<String, Object> params = new HashMap<>();
		params.put("quizId", quizId);

		try {
			System.out.println("Executing SQL: " + deleteAttemptsSql + " with params: " + params);
			jdbcTemplate.update(deleteAttemptsSql, params);

			System.out.println("Executing SQL: " + deleteOptionsSql + " with params: " + params);
			jdbcTemplate.update(deleteOptionsSql, params);

			System.out.println("Executing SQL: " + deleteQuestionsSql + " with params: " + params);
			jdbcTemplate.update(deleteQuestionsSql, params);

			System.out.println("Executing SQL: " + deleteQuizSql + " with params: " + params);
			jdbcTemplate.update(deleteQuizSql, params);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			throw new QuizServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(), ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
		}
	}

	@Override
	public Quiz getQuizById(Long quizId) {
		System.out.println("QuizDaoImpl.getQuizById invoked");

		String quizSql = "SELECT * FROM quizzes WHERE id = :id";
		Map<String, Object> quizParams = new HashMap<>();
		quizParams.put("id", quizId);

		Quiz quiz = null;
		try {
			quiz = jdbcTemplate.queryForObject(quizSql, quizParams, (rs, rowNum) -> {
				Quiz q = new Quiz();
				q.setId(rs.getLong("id"));
				q.setTitle(rs.getString("title"));
				return q;
			});
		} catch (Exception e) {
			System.out.println("Exception:" + e);
			return null;
		}

		String questionsSql = "SELECT * FROM questions WHERE quiz_id = :quizId";
		Map<String, Object> questionParams = new HashMap<>();
		questionParams.put("quizId", quizId);

		List<Question> questions = null;

		try {
			questions = jdbcTemplate.query(questionsSql, questionParams, (rs, rowNum) -> {
				Question question = new Question();
				question.setId(rs.getLong("id"));
				question.setTitle(rs.getString("title"));
				question.setCorrectAnswer(rs.getString("correct_answer"));

				String optionsSql = "SELECT option_text FROM options WHERE question_id = :questionId";
				Map<String, Object> optionParams = new HashMap<>();
				optionParams.put("questionId", question.getId());
				List<String> options = jdbcTemplate.query(optionsSql, optionParams,
						(optionRs, optionRowNum) -> optionRs.getString("option_text"));

				question.setOptions(options);
				return question;
			});
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}

		quiz.setQuestions(questions);
		return quiz;
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		System.out.println("QuizDaoImpl.getAllQuizzes invoked");

		String quizSql = "SELECT * FROM quizzes";
		try {
			return jdbcTemplate.query(quizSql, (rs, rowNum) -> {
				Quiz quiz = new Quiz();
				quiz.setId(rs.getLong("id"));
				quiz.setTitle(rs.getString("title"));
				return quiz;
			});
		} catch (Exception e) {
			System.out.println("Exception:" + e);
			return new ArrayList<>();
		}
	}
}
