package com.nrenaud.pyj.models

data class MarkerDto(
        val id : String? = null,
        val groupId : String,
        val name : String = "",
        val interestId : String? = null,
        val lat : Float,
        val lng : Float)