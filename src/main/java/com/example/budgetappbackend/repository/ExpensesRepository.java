package com.example.budgetappbackend.repository;

import com.example.budgetappbackend.model.Expenses;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
    List<Expenses> findByUserId(Long userId);

    @Query(value = "select * from expenses where user_id = :userId and what_time >= :startDate and what_time <= :endDate order by what_time desc", nativeQuery = true)
    List<Expenses> findRelevantExpenses(@Param("userId") Long userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select a from Expenses a where a.userId = :userId and a.whatTime >= :startDate and a.whatTime <= :endDate and a.price >= :minValue and a.price <= :maxValue and (a.necessary = true or a.necessary = :includeUnnecessary)")
    List<Expenses> findExpensesForCSV(Sort sort, @Param("userId") Long userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("minValue") Long minValue, @Param("maxValue") Long maxValue, @Param("includeUnnecessary") boolean includeUnnecessary);

}
