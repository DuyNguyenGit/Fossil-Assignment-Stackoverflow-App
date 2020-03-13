package com.fossil.duy.stackoverflow.users.models

import com.fossil.duy.stackoverflow.common.Mapper
import com.fossil.duy.stackoverflow.users.data.UsersDao
import org.joda.time.DateTime
import javax.inject.Inject

class UsersEntityMapper @Inject constructor(val dao: UsersDao) : Mapper<UsersResponse, List<UserEntity>>() {
    override fun mapFrom(from: UsersResponse): List<UserEntity> {

        return from.items?.map {
            val existUser = dao.getUserNoLiveDataNoSuspend(it.user_id.toLong())
            if (existUser == null) {
                UserEntity(
                    it?.user_id?.toLong() ?: throw IllegalArgumentException("User does not have an id"),
                    it.display_name ?: "",
                    it.reputation?.toLong() ?: 0,
                    it.profile_image ?: "",
                    it.location ?: "",
                    it.last_access_date.toLong() ?: 0,
                    false
                )
            } else {
                UserEntity(
                    it?.user_id?.toLong() ?: throw IllegalArgumentException("User does not have an id"),
                    it.display_name ?: "",
                    it.reputation?.toLong() ?: 0,
                    it.profile_image ?: "",
                    it.location ?: "",
                    it.last_access_date.toLong() ?: 0,
                    existUser.isBookmarked
                )
            }

        } ?: emptyList()
    }

    fun mapFromRoom(from: List<UserEntity>): List<UserDomain> {
        return from.map { mapFromRoom(it) }
    }

    fun mapFromRoom(from: UserEntity): UserDomain {
        return UserDomain(
            from.id,
            from.name,
            from.reputation,
            from.profileImageUrl,
            from.location,
            DateTime(from.lastAccessDate),
            from.isBookmarked
        )
    }

    fun mapToRoom(from: List<UserDomain>): List<UserEntity> {
        return from.map { mapToRoom(it) }
    }

    fun mapToRoom(from: UserDomain): UserEntity {
        return UserEntity(
            from.id,
            from.name,
            from.reputation,
            from.profileImageUrl,
            from.location,
            from.lastAccessDate.millis,
            from.isBookmarked
        )
    }
}