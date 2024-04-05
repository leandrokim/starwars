package com.leandrokim.people

import com.leandrokim.people.di.ActionFactory
import com.leandrokim.people.domain.action.GetPeople
import com.leandrokim.people.domain.action.GetPerson

object PeopleModule {
    fun getPeopleAction(): GetPeople {
        return ActionFactory.createGetPeople()
    }

    fun getPersonAction(): GetPerson {
        return ActionFactory.createGetPerson()
    }
}