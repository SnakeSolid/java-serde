package ru.snake.serde.bench;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {

	private UUID transactionId;

	private double amount;

	private LocalDate date;

	private TransactionStatus status;

	public Transaction() {
	}

	public Transaction(UUID transactionId, double amount, LocalDate date, TransactionStatus status) {
		this.transactionId = transactionId;
		this.amount = amount;
		this.date = date;
		this.status = status;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public TransactionStatus getStatus() {
		return status;
	}

}