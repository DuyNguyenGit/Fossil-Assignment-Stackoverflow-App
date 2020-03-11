package com.fossil.duy.stackoverflow.users.models

import org.joda.time.DateTime

data class UserDomain(
    val id: Long,
    val name: String,
    val reputation: Long,
    val profileImageUrl: String,
    val location: String,
    val lastAccessDate: DateTime,
    val isBookmarked: Boolean
)