package com.innobytes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.innobytes.constants.ErrorCodeEnum;
import com.innobytes.dao.QuizDao;
import com.innobytes.dto.Quiz;
import com.innobytes.exception.QuizServiceException;
import com.innobytes.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Override
	public void createQuiz(Quiz quiz) {
		System.out.println("QuizServiceImpl.createQuiz invoked");
		quizDao.createQuiz(quiz);
	}

	@Override
	public void updateQuiz(Quiz quiz) {
		System.out.println("QuizServiceImpl.updateQuiz invoked");
		quizDao.updateQuiz(quiz);
	}

	@Override
	public void deleteQuiz(Long quizId) {
		System.out.println("QuizServiceImpl.deleteQuiz invoked");
		quizDao.deleteQuiz(quizId);
	}

	@Override
	public Quiz getQuizById(Long quizId) {
		System.out.println("QuizServiceImpl.getQuizById invoked");
		Quiz quiz = quizDao.getQuizById(quizId);
		if(quiz == null) {
			System.out.println("Quiz not found for given id || quizId:" + quizId);
			throw new QuizServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(),
					ErrorCodeEnum.GENERIC_EXCEPTION.getErrorMessage());
		}
		return quiz;
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		System.out.println("QuizServiceImpl.getAllQuizzes invoked");
		return quizDao.getAllQuizzes();
	}
}