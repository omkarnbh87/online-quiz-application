package com.innobytes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobytes.dao.QuizAttemptDao;
import com.innobytes.dao.QuizDao;
import com.innobytes.dao.UserDao;
import com.innobytes.dto.Quiz;
import com.innobytes.dto.QuizAttempt;
import com.innobytes.dto.User;
import com.innobytes.service.QuizAttemptService;

@Service
public class QuizAttemptServiceImpl implements QuizAttemptService {

    @Autowired
    private QuizAttemptDao quizAttemptDAO;

    @Autowired
    private QuizDao quizDAO;

    @Autowired
    private UserDao userDAO;

    @Override
    public QuizAttempt attemptQuiz(Long quizId, String username, List<String> answers) {
    	System.out.println("QuizAttemptDao.attemptQuiz invokrd");
        User user = userDAO.getUserByEmail(username);
        Quiz quiz = quizDAO.getQuizById(quizId);
        int score = calculateScore(quiz, answers);

        QuizAttempt attempt = new QuizAttempt();
        attempt.setUserId(user.getId());
        attempt.setQuizId(quiz.getId());
        attempt.setScore(score);

        quizAttemptDAO.saveQuizAttempt(attempt);
        System.out.println("returning response || attempt:" + attempt);
        return attempt;
    }

    @Override
    public List<QuizAttempt> getUserQuizAttempts(String username) {
    	System.out.println("QuizAttemptDao.getUserQuizAttempts invoked");
        User user = userDAO.getUserByEmail(username);
        List<QuizAttempt> response = quizAttemptDAO.getQuizAttemptsByUserId(user.getId());
        System.out.println("returning response:" + response);
        return response;
    }

    private int calculateScore(Quiz quiz, List<String> answers) {
        int score = 0;
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            if (quiz.getQuestions().get(i).getCorrectAnswer().equals(answers.get(i))) {
                score++;
            }
        }
        return score;
    }
}
