package ru.snake.serde.bench;

import java.time.LocalDate;

public class Employee {

	private int employeeId;

	private String fullName;

	private String position;

	private double salary;

	private LocalDate hireDate;

	public Employee() {
	}

	public Employee(int employeeId, String fullName, String position, double salary, LocalDate hireDate) {
		this.employeeId = employeeId;
		this.fullName = fullName;
		this.position = position;
		this.salary = salary;
		this.hireDate = hireDate;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPosition() {
		return position;
	}

	public double getSalary() {
		return salary;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

}