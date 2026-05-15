package com.masefal_0046.tigaunifess.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pesan",
    foreignKeys = [
        ForeignKey(
            entity = Kategori::class,
            parentColumns = ["id"],
            childColumns = ["idKategori"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idKategori"])]
)
data class Pesan(
    @PrimaryKey(autoGenerate = true) val id:Long = 0L,
    val konten: String,
    val pengirim: String = "Anonymous",
    val idKategori: Long,
    val tanggal: Long = System.currentTimeMillis(),
    val isDelete: Boolean = false
)