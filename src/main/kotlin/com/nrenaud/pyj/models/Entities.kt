package com.nrenaud.pyj.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Markers")
data class Marker(
        @Id
        val id: String,
        val groupId: String,
        val name : String = "",
        val interestId : String? = null,
        val lat: Float,
        val lng: Float
)