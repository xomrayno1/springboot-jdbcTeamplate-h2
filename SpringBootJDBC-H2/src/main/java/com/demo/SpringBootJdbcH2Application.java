package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demo.model.Student;
import com.demo.repository.StudentRepository;
/*	How to use Spring Boot Started JDBC?
	How to connect a Spring Boot project to database using Spring JDBC?
	How to write a simple repository class with all the CRUD methods?
	How to execute basic queries using Spring JDBC?
	How to create a project using Spring Boot, Spring JDBC and H2?
	What are the basics of an in memory database?
 * https://www.springboottutorial.com/spring-boot-and-spring-jdbc-with-h2
 */
@SpringBootApplication
public class SpringBootJdbcH2Application implements CommandLineRunner{

	@Autowired
	StudentRepository studentRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcH2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("All Student");
		 studentRepository.findAll().forEach(System.out::println);
		 System.out.println("Get By Id");
		 Student student = studentRepository.findById(3);
		 if(student != null) {
			 System.out.println( studentRepository.findById(3)); 
		 }
		 System.out.println("Find By LastName");
		 System.out.println( studentRepository.findStudentByLastName("2"));
		 
		 System.out.println("create");
		 studentRepository.create(new Student("Tam", "5"));
		 System.out.println("update id = 1");
		 studentRepository.update(new Student(1,"Tam", "6,"));
		 System.out.println("delete Id = 3");
		 studentRepository.deleteById(3);
		 
		 System.out.println("All Student");
		 studentRepository.findAllQ().forEach(System.out::println);
		 System.out.println("count student");
		 System.out.println(studentRepository.getCountStudent());
	}
 
}
