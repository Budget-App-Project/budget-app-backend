package com.example.budgetappbackend.repository;

import com.example.budgetappbackend.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
    List<Expenses> findByUser_id(Long user_id);
}
