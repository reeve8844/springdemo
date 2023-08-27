package com.example.springdemo.rowMapper;

import com.example.springdemo.entity.Users;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<Users> {
    Users user=new Users();
    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreated_at(rs.getTime("create_at"));
        user.setUpdated_at(rs.getTime("update_at"));
        return user;
    }
}
