package com.example.mobile_application_final.data.models

import com.google.firebase.firestore.DocumentId

data class Category (
    @DocumentId
    val id: String = "",
    val category: String = ""
)