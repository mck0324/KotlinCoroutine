package com.example.webfluxcoroutine.service

import com.example.webfluxcoroutine.exception.NoArticleFound
import com.example.webfluxcoroutine.model.Article
import com.example.webfluxcoroutine.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Service
class ArticleService(
    @Autowired private val repository: ArticleRepository
) {
    suspend fun create(@RequestBody request: ReqCreate): Article {
        return repository.save(request.toArticle())
    }

    suspend fun get(id: Long): Article {
        return repository.findById(id) ?: throw NoArticleFound("id: $id")
    }

    suspend fun getAll(@RequestParam title: String? = null): Flow<Article> {
        return if (title.isNullOrEmpty()) {
            repository.findAll()
        } else {
            repository.findAllByTitleContains(title)
        }
    }

    suspend fun update(id: Long, request: ReqUpdate): Article {
        val article = repository.findById(id) ?: throw NoArticleFound("id: $id")
        return repository.save(article.apply {
            request.title?.let { title = it }
            request.body?.let { body = it }
            request.authorId?.let { authorId = it }
        })

    }

    suspend fun delete(id: Long) {
        return repository.deleteById(id)
    }

}

data class ReqUpdate (
    val title: String,
    val body: String? = null,
    val authorId: Long?? = null
)


data class ReqCreate (
    val title: String,
    val body: String? = null,
    val authorId: Long? = null
) {
    fun toArticle(): Article {
        return Article(
            title = this.title,
            body = this.body,
            authorId = this.authorId,
        )
    }
}

