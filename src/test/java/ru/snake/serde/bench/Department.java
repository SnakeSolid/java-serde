package ru.snake.serde.bench;

import java.util.UUID;

public class Department extends Organization {

	private double budget;

	private Employee[] employees;

	public Department() {
	}

	public Department(
		UUID id,
		String name,
		OrganizationType type,
		int foundedYear,
		double budget,
		Employee[] employees
	) {
		super(id, name, type, foundedYear);
		this.budget = budget;
		this.employees = employees;
	}

	public double getBudget() {
		return budget;
	}

	public Employee[] getEmployees() {
		return employees;
	}

}