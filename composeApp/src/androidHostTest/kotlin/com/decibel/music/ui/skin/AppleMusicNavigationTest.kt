package com.decibel.music.ui.skin

import com.decibel.music.ui.navigation.destination.home.MoodDestination
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AppleMusicNavigationTest {
    @Test
    fun browseTabUsesConcreteMoodRouteInstance() {
        val destination = assertIs<MoodDestination>(AppleMusicNavTab.Browse.destination)

        assertEquals("ggMPOg1uX0NoYXJ0cw%3D%3D", destination.params)
        assertEquals(MoodDestination::class, AppleMusicNavTab.Browse.routeClass)
    }
}
