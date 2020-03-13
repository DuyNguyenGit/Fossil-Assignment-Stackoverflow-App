package com.fossil.duy.stackoverflow.common

import com.fossil.duy.stackoverflow.database.DataResult


fun <T> T.toResult(): DataResult<T> = DataResult.success(this)