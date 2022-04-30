package com.an_mikhaylov.bluetoothmonitor

import java.io.Serializable

data class ListItem(
    var name: String,
    var mac: String
): Serializable
