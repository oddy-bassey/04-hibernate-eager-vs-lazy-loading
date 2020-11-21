package com.revoltcode.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.revoltcode.demo.entity.Course;
import com.revoltcode.demo.entity.Instructor;
import com.revoltcode.demo.entity.InstructorDetail;

public class EagerLazyLoadingApplication {
	
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(InstructorDetail.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try { 
			session.beginTransaction();
			 
			//get the instructor from the database
			Instructor instructor = session.get(Instructor.class, 5);
			
			if(instructor != null) {
				
				//get instructor
				System.out.println(">> Instructor Detail - "+instructor);
				
				//get instructor
				System.out.println(">> Instructor Detail - "+instructor.getInstructorDetail());
				
				//get instructor courses
				System.out.println(">> Instructor Course Detail - "+instructor.getCourse());
			}else {
				System.out.println(">> No Such Instructor Exists.");
			}
			
			//session.getTransaction().commit();
			
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			session.close();
			factory.close();
		}
	}
}
