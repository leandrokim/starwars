package com.leandrokim.starwars.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandrokim.people.domain.action.GetPeople
import com.leandrokim.people.domain.model.Person
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val getPeople: GetPeople) : ViewModel() {

    private val people = MutableStateFlow<List<Person>>(emptyList())
    private val loading = MutableStateFlow(false)
    private val error = MutableStateFlow(false)

    private var page = 1

    val state = combine(people, loading, error) { people, loading, error ->
        HomeState(people, loading, error)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (loading.value || page < 1) return
        error.update { false }
        loading.update { true }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val peoplePage = getPeople(page)
                page++
                people.update { addPageToCurrentList(peoplePage) }
                loading.update { false }
                if (isLastPage(peoplePage)) page = -1
            } catch (e: Exception) {
                error.update { true }
                loading.update { false }
            }
        }
    }

    private fun isLastPage(peoplePage: List<Person>): Boolean {
        return peoplePage.isEmpty()
    }

    private fun addPageToCurrentList(peoplePage: List<Person>): List<Person> {
        val currentList = people.value.toMutableList()
        currentList.addAll(peoplePage)
        return currentList
    }

}

data class HomeState(
    val people: List<Person>,
    val loadingL: Boolean,
    val error: Boolean
)
