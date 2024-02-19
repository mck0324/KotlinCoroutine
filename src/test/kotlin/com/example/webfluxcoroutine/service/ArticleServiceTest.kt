package com.example.webfluxcoroutine.service

import com.example.webfluxcoroutine.repository.ArticleRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ArticleServiceTest(
    @Autowired private val service: ArticleService,
    @Autowired private val repository: ArticleRepository,
) : StringSpec({

    beforeTest {
        repository.deleteAll()
    }

    "create and get" {
        val preCnt = repository.count()
        val created = service.create(ReqCreate("title1"))
        val get = service.get(created.id)
        get.id shouldBe created.id
        get.title shouldBe created.title
        get.authorId shouldBe created.authorId
        get.createdAt shouldNotBe null
        get.updatedAt shouldNotBe null
    }

    "get all" {
        service.create(ReqCreate("title1"))
        service.create(ReqCreate("title2"))
        service.create(ReqCreate("title matched"))
        service.getAll().toList().size shouldBe 3
        service.getAll("matched").toList().size shouldBe 1
    }

    "update" {
        val created = service.create(ReqCreate("title1"))
        service.update(created.id,ReqUpdate("hihi","hihihi3"))
        val updated = service.get(created.id)
        updated.title shouldBe "hihi"
        updated.body shouldBe "hihihi3"
    }

    "delete" {
        val preCnt = repository.count()
        val created = service.create(ReqCreate("title1"))
        repository.count() shouldBe preCnt + 1
        service.delete(created.id)
        repository.count() shouldBe preCnt

    }
})


