package com.fossil.duy.stackoverflow.userdetail.models

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("items") val userDetails: List<UserDetailEntity>,
    @SerializedName("has_more") val has_more: Boolean,
    @SerializedName("quota_max") val quota_max: Int,
    @SerializedName("quota_remaining") val quota_remaining: Int
)