package ru.snake.serde.bench;

import java.util.UUID;

public class Organization {

	private UUID id;

	private String name;

	private OrganizationType type;

	private int foundedYear;

	public Organization() {
	}

	public Organization(UUID id, String name, OrganizationType type, int foundedYear) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.foundedYear = foundedYear;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public OrganizationType getType() {
		return type;
	}

	public int getFoundedYear() {
		return foundedYear;
	}

}