package com.decibel.music.ytmusic.models

sealed class MediaType {
    data object Song : MediaType()

    data object Video : MediaType()
}