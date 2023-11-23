package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.manytomany.type1.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
