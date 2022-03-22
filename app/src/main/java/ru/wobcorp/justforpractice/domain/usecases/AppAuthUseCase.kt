package ru.wobcorp.justforpractice.domain.usecases

import io.reactivex.rxjava3.core.Single
import ru.wobcorp.justforpractice.data.repositories.UserDataRepository
import javax.inject.Inject

class AppAuthUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {

    companion object {
        private const val DEF_VALUE_LOGIN = ""
        private const val DEF_VALUE_PASSWORD = -1
    }

    fun execute(inputLogin: String?, inputPassword: String?): Single<Boolean> {
        val login = parseLogin(inputLogin)
        val password = parsePassword(inputPassword)
        return userDataRepository.checkAuthSuccess(login, password)
    }

    private fun parseLogin(inputLogin: String?): String {
        return inputLogin?.trim() ?: DEF_VALUE_LOGIN
    }

    private fun parsePassword(inputPassword: String?): Int {
        return try {
            inputPassword?.trim()?.toInt() ?: DEF_VALUE_PASSWORD
        } catch (e: Exception) {
            DEF_VALUE_PASSWORD
        }
    }
}