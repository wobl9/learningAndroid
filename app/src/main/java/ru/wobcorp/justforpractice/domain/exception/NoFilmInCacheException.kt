package ru.wobcorp.justforpractice.domain.exception

import java.lang.NullPointerException

class NoFilmInCacheException(val id: Int) : NullPointerException("Film with id: $id is absent")