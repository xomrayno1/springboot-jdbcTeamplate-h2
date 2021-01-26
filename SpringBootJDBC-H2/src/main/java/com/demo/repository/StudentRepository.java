package com.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.demo.model.Student;
/*
 * 
 */
@Repository
public class StudentRepository {

	@Autowired
	JdbcTemplate template;
	@Autowired
	DataSource dataSource;
	//using query  
	public List<Student> findAllQ()  {
		String sql = "select * from student";
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement 
				= connection.prepareStatement(sql);
			ResultSet resultSet = 	statement.executeQuery();
			List<Student> list = new ArrayList<Student>();
			while(resultSet.next()) {
				Student student = new Student();
				student.setFirstName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				student.setId(resultSet.getInt("id"));
				list.add(student);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	public void create(Student student) {
		 String sql = "Insert into student(first_name, last_name) values (?,?)";
		 template.update(sql,student.getFirstName(),student.getLastName());
	}
	public void update(Student student) {
		 String sql = "Update student set first_name = ?, last_name = ? where id  = ?";
		 template.update(sql,student.getFirstName(),student.getLastName(), student.getId());
	}
	@SuppressWarnings("deprecation")
	public Student findById(int id) {
		String sql = "select * from student where id = ?";
		return template.queryForObject(sql, new Object[] {id} 
		, new BeanPropertyRowMapper<Student>(Student.class)  );
	}
	public List<Student> findAll(){
		String sql = "select * from student";
		return template.query(sql, new RowMapper<Student>() {

			@Override
			public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Student student  = new Student();
				student.setFirstName(rs.getString("first_name"));
				student.setId(rs.getInt("id"));
				student.setLastName(rs.getString("last_name"));
				return student;
			}	
		});
		 
	}
	public void deleteById(int id) {
		String sql = "delete student where id = ?";
		template.update(sql,id);
	}
	public Integer getCountStudent() {
		String sql = "select count(*) from student";
		return template.queryForObject(sql, Integer.class);
	}
	 
	class StudentRowMapper implements RowMapper<Student>{

		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Student student  = new Student();
			student.setFirstName(rs.getString("first_name"));
			student.setId(rs.getInt("id"));
			student.setLastName(rs.getString("last_name"));
			return student;
		}
	}
	public Student findStudentByLastName(String lastName) {
		String sql = "select * from student where last_name = ?";
		
		return template.queryForObject(sql,
				new Object[] {lastName} ,
				new StudentRowMapper());
	}
}
