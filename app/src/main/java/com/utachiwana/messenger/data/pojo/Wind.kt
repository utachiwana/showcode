package com.utachiwana.messenger.data.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Wind(
    @ColumnInfo(name = "deg")
    val deg: Int,
    @ColumnInfo(name = "speed")
    val speed: Double
)