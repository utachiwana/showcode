package com.utachiwana.messenger.main.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Weather(
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "icon")
    val icon: String,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "main")
    val main: String
)