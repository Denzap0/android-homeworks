package com.example.homework5_2.Contact

import androidx.room.Entity
import java.io.Serializable

class Contact(var name: String, var communication: String, var connectType: ConnectType) :
    Serializable