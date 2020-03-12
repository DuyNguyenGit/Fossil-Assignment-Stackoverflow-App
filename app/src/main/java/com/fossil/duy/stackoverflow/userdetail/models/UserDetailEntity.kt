package com.fossil.duy.stackoverflow.userdetail.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_details")
data class UserDetailEntity(
    @PrimaryKey
    val id: String,
    @field:SerializedName("reputation_history_type")
    val reputation_history_type: String,
    @field:SerializedName("reputation_change")
    val reputation_change: Int?,
    @field:SerializedName("post_id")
    val post_id: Int? = null,
    @field:SerializedName("creation_date")
    val creation_date: Int? = null,
    @field:SerializedName("user_id")
    val user_id: Int? = null
)