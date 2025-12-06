package com.example.mobile_application_final.data.models

import com.google.firebase.firestore.DocumentId

data class User (
    @DocumentId
    val id: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = ""
)