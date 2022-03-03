package ru.wobcorp.justforpractice.domain.usecases

import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.data.repositories.UserDataRepository
import javax.inject.Inject

class AppAuthUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    fun execute(inputLogin: String?, inputPassword: String?): Single<Boolean> {
        return userDataRepository.checkAuthSuccess(inputLogin, inputPassword)
    }
}