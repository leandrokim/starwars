package com.leandrokim.starwars.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandrokim.people.domain.action.GetPerson
import com.leandrokim.people.domain.model.Person
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(private val getPersonAction: GetPerson) : ViewModel() {

    private val person = MutableStateFlow<Person?>(null)
    private val loading = MutableStateFlow(true)
    private val error = MutableStateFlow(false)

    val state = combine(person, loading, error) { person, loading, error ->
        DetailsState(person, loading, error)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun getPerson(name: String) {
        loading.update { true }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val foundPerson = getPersonAction(name)
                person.update { foundPerson }
                loading.update { false }
            } catch (e: Exception) {
                error.update { true }
                loading.update { false }
            }
        }
    }
}

data class DetailsState(
    val person: Person?,
    val loading: Boolean,
    val error: Boolean
)