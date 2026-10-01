package com.decibel.music.domain.data.model.searchResult

import com.decibel.music.domain.data.type.SearchResultType

data class SearchSuggestions(
    val queries: List<String>,
    val recommendedItems: List<SearchResultType>,
)