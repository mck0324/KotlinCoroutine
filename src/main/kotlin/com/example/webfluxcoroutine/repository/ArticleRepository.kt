package com.example.webfluxcoroutine.repository

import com.example.webfluxcoroutine.model.Article
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository: CoroutineCrudRepository<Article, Long> {
}