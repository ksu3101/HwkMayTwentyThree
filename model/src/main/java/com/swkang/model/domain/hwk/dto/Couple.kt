package com.swkang.model.domain.hwk.dto

data class Couple(
    val intersection: String,
    val i1: Int,
    val i2: Int,
    val items1: List<String>,
    val items2: List<String>
)