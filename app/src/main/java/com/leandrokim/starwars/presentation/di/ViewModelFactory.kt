package com.leandrokim.starwars.presentation.di

import com.leandrokim.people.PeopleModule
import com.leandrokim.starwars.ui.viewModel.DetailsViewModel
import com.leandrokim.starwars.ui.viewModel.HomeViewModel

object ViewModelFactory {
    fun createHomeViewModel() = HomeViewModel(PeopleModule.getPeopleAction())

    fun createDetailsViewModel() = DetailsViewModel(PeopleModule.getPersonAction())
}