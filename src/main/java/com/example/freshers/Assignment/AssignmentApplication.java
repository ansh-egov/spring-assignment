package com.example.freshers.Assignment;

import com.example.freshers.Assignment.Reflection.Employee;
import com.example.freshers.Assignment.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.*;

@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		SpringApplication.run(AssignmentApplication.class, args);

		// reflection demo
		Class c = Class.forName("com.example.freshers.Assignment.Reflection.Employee");

		// Listing all the constructor methods
		Constructor[] constructors = c.getDeclaredConstructors();
		for(Constructor constructor: constructors){
			System.out.println("Name of Constructor : "+constructor);

			System.out.println("Count of constructor parameter : "+constructor.getParameterCount());

			Parameter[] parameters = constructor.getParameters();
			for(Parameter parameter : parameters) {
				System.out.println("Constructor's parameter : "+parameter);
			}
			System.out.println();
		}

		System.out.println();

		Employee ansh = new Employee(1,"Ansh",100);
		System.out.println(ansh.toString());

		Field privateFieldName = Employee.class.getDeclaredField("salary");
		privateFieldName.setAccessible(true);

		privateFieldName.setLong(ansh,100000);
		System.out.println();
		System.out.println(ansh.toString());

		// Method Invocation
		Method privateMethod = Employee.class.getDeclaredMethod("getSalary");

		privateMethod.setAccessible(true);

		System.out.println(privateMethod.invoke(ansh));
		System.out.println(ansh.toString());
	}

}
