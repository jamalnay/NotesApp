package com.lamda.projectnotes.domain.use_cases.category_use_cases

import com.lamda.projectnotes.domain.repository.CategoryRepository

class GetNotesCountForCategory(private val repository: CategoryRepository) {
    suspend operator fun invoke (category: Int): Int = repository.getNotesCountForCategory(category = category)
}