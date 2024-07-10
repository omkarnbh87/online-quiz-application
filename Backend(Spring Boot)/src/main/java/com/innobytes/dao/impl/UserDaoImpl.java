package com.innobytes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.innobytes.dao.UserDao;
import com.innobytes.dto.User;
import com.innobytes.pojo.SignUpRequest;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public boolean saveUser(SignUpRequest signUpRequest) {
		System.out.println("UserDaoImpl.saveUser invoked");

		String sql = "INSERT INTO users (first_name, last_name, email, password, role) "
				+ "VALUES (:firstName, :lastName, :email, :password, :role)";

		System.out.println("query for saveUser || sql:" + sql);

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(signUpRequest);
		int rowsAffected = namedJdbcTemplate.update(sql, paramSource);

		return rowsAffected > 0;
	}

	@Override
	public User getUserByEmail(String email) {
		System.out.println("invoked UserDaoImpl.getUserByEmail");
		String sql = "SELECT id, first_name, last_name, email, password, role, created_at FROM users WHERE email = :email";

		System.out.println("query for getUserByEmail || sql:" + sql);

		SqlParameterSource paramSource = new MapSqlParameterSource("email", email);

		try {
			return namedJdbcTemplate.queryForObject(sql, paramSource, new BeanPropertyRowMapper<>(User.class));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("exception:" + e);
			return null;
		}
	}

	@Override
	public User getUserByRoleId(int roleId) {
		System.out.println("invoked UserDaoImpl.getUserByRoleId");
		String sql = "SELECT id, first_name, last_name, email, password, role, created_at FROM users WHERE role = :roleId";

		System.out.println("query for getUserByRoleId || sql:" + sql);

		SqlParameterSource paramSource = new MapSqlParameterSource("roleId", roleId);

		try {
			return namedJdbcTemplate.queryForObject(sql, paramSource, new BeanPropertyRowMapper<>(User.class));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("exception:" + e);
			return null;
		}
	}

}
