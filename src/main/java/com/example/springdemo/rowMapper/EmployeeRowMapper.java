package com.example.springdemo.rowMapper;

import com.example.springdemo.entity.Employees;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employees> {
    Employees employees = new Employees();
    @Override
    public Employees mapRow(ResultSet rs, int rowNum) throws SQLException {
        employees.setId(rs.getInt("id"));
        employees.setName(rs.getString("name"));
        employees.setPassword(rs.getString("password"));
        employees.setIs_delete(rs.getBoolean("is_delete"));
        employees.setCreated_at(rs.getTime("create_at"));
        employees.setUpdated_at(rs.getTime("update_at"));
        return employees;
    }
}
