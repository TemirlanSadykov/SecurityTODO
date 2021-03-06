package com.example.ooo.backend.repository;

import com.example.ooo.backend.model.AbstractEntity;
import com.example.ooo.backend.model.QTodo;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface ExCustomRepository<T extends AbstractEntity, P extends EntityPathBase<T>, ID extends Serializable>
        extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<P> {

    default void customize(QuerydslBindings querydslBindings, QTodo qTodo) {
    }
}