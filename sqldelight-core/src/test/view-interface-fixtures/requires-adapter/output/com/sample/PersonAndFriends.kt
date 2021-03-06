package com.sample

import com.squareup.Redacted
import java.util.List
import kotlin.Double
import kotlin.String

interface PersonAndFriends {
    val full_name: String

    val friends: List<Person>?

    val shhh_its_secret: @Redacted String

    val casted: Double

    data class Impl(
            override val full_name: String,
            override val friends: List<Person>?,
            override val shhh_its_secret: @Redacted String,
            override val casted: Double
    ) : PersonAndFriends
}

abstract class PersonAndFriendsModel : PersonAndFriends {
    final override val full_name: String
        get() = full_name()

    final override val friends: List<Person>?
        get() = friends()

    final override val shhh_its_secret: @Redacted String
        get() = shhh_its_secret()

    final override val casted: Double
        get() = casted()

    abstract fun full_name(): String

    abstract fun friends(): List<Person>?

    abstract fun shhh_its_secret(): @Redacted String

    abstract fun casted(): Double
}
