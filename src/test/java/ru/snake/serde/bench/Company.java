package ru.snake.serde.bench;

import java.time.LocalDate;
import java.util.UUID;

public class Company extends Organization {

	private String address;

	private double budget;

	private Department[] departments;

	private Transaction[] transactions;

	public Company() {
	}

	public Company(
		UUID id,
		String name,
		OrganizationType type,
		int foundedYear,
		String address,
		double budget,
		Department[] departments,
		Transaction[] transactions
	) {
		super(id, name, type, foundedYear);
		this.address = address;
		this.budget = budget;
		this.departments = departments;
		this.transactions = transactions;
	}

	public String getAddress() {
		return address;
	}

	public double getBudget() {
		return budget;
	}

	public Department[] getDepartments() {
		return departments;
	}

	public Transaction[] getTransactions() {
		return transactions;
	}

	public static Company createCompany(final int nDepartments, final int nEmployees, final int nTransactions) {
		Department[] departments = new Department[nDepartments];

		for (int i = 0; i < nDepartments; i++) {
			Employee[] employees = new Employee[nEmployees];

			for (int j = 0; j < nEmployees; j++) {
				employees[j] = new Employee(
					j,
					"Employee " + j,
					"Position " + (j % 10),
					1000 + j % 500,
					LocalDate.now().minusDays(j)
				);
			}

			departments[i] = new Department(
				UUID.randomUUID(),
				"Department " + i,
				OrganizationType.COMMERCIAL,
				2000 + i,
				1_000_000,
				employees
			);
		}

		Transaction[] transactions = new Transaction[nTransactions];

		for (int i = 0; i < nTransactions; i++) {
			transactions[i] = new Transaction(
				UUID.randomUUID(),
				1000 + i % 500,
				LocalDate.now().minusDays(i),
				TransactionStatus.values()[i % 3]
			);
		}

		return new Company(
			UUID.randomUUID(),
			"Mega Corp",
			OrganizationType.COMMERCIAL,
			1990,
			"Global Headquarters",
			1_000_000_000,
			departments,
			transactions
		);
	}

}