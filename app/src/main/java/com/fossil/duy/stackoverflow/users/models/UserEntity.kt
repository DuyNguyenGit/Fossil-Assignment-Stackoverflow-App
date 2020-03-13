package com.fossil.duy.stackoverflow.users.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

@Entity(tableName = "users")
data class UserEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "reputation") val reputation: Long,
    @ColumnInfo(name = "profileImageUrl") val profileImageUrl: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "lastAccessDate") val lastAccessDate: Long,
    @ColumnInfo(name = "isBookmarked") var isBookmarked: Boolean
)

fun List<UserEntity>.asDomainModel(): List<UserDomain> {
    return map {
        it.mapToUserDomain()
    }
}

fun UserEntity.mapToUserDomain(): UserDomain {
    return UserDomain(
        id = this.id,
        name = this.name,
        reputation = this.reputation,
        profileImageUrl = this.profileImageUrl,
        location = this.location,
        lastAccessDate = DateTime(this.lastAccessDate, DateTimeZone.UTC),
        isBookmarked = this.isBookmarked
    )
}