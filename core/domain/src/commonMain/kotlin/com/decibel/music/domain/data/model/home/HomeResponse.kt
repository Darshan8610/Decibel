package com.decibel.music.domain.data.model.home

import com.decibel.music.domain.data.model.home.chart.Chart
import com.decibel.music.domain.data.model.mood.Mood
import com.decibel.music.domain.utils.Resource

data class HomeResponse(
    val homeItem: Resource<ArrayList<HomeItem>>,
    val exploreMood: Resource<Mood>,
    val exploreChart: Resource<Chart>,
)