package com.example.budgetappbackend.repository;

import com.example.budgetappbackend.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
}
