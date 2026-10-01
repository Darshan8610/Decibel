package com.decibel.music.domain.repository

import com.decibel.music.domain.data.model.update.UpdateData
import com.decibel.music.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UpdateRepository {
    fun checkForGithubReleaseUpdate(): Flow<Resource<UpdateData>>
    fun checkForFdroidUpdate(): Flow<Resource<UpdateData>>
}