package com.nrenaud.pyj.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Markers")
data class Marker(
        val groupId: String,
        val lat: Float,
        val lng: Float,
        @Id
        val id: String
)