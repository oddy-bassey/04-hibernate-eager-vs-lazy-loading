
package com.revoltcode.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.revoltcode.demo.entity.Course;
import com.revoltcode.demo.entity.Instructor;
import com.revoltcode.demo.entity.InstructorDetail;

public class EagerLazyLoadingSessionCloseApplication2 {
	
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
			 
			//Solution 2-->get the instructor from the database
			Query<Instructor> query = session.createQuery("select i from Instructor i JOIN FETCH i.course where i.id=:theInstructorId");
			
			//set parameter on query
			query.setParameter("theInstructorId", 5);
			
			//execute query and get instructor
			Instructor instructor = query.getSingleResult();
			
			if(instructor != null) {
				
				//get instructor
				System.out.println(">> Instructor Detail - "+instructor);
				
				//get instructor
				System.out.println(">> InstructorDetail Detail- "+instructor.getInstructorDetail());
				
				session.getTransaction().commit();
				System.out.println("Session Closed!");
				session.close();
				
				/*
				 * Solution 2: Load data using HQL query
				*/
				//get instructor courses
				System.out.println(">> Instructor Course Detail - "+instructor.getCourse());
			}else {
				System.out.println(">> No Such Instructor Exists.");
			}
			
			//session.getTransaction().commit();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			session.close();
			factory.close();
		}
	}
}
