package com.example.webfluxcoroutine

import com.example.webfluxcoroutine.model.Article
import com.example.webfluxcoroutine.repository.ArticleRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class WebfluxCoroutineApplicationTests(
    @Autowired private val repository: ArticleRepository,
): StringSpec ({
    "context load".config(false) {
        val prev = repository.count()
        repository.save(Article(title = "hihi"))
        val curr = repository.count()
        curr shouldBe prev + 1
//        Assertions.assertEquals(prev+1,curr)
    }
})

