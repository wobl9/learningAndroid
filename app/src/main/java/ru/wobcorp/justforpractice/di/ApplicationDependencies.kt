package ru.wobcorp.justforpractice.di

import ru.wobcorp.justforpractice.data.remote.services.FilmsService

interface ApplicationDependencies {
    val remoteService: FilmsService
}