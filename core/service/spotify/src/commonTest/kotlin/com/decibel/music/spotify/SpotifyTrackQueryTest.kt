package com.decibel.music.spotify

import kotlin.test.Test
import kotlin.test.assertEquals

class SpotifyTrackQueryTest {
    @Test
    fun joinsCleanTitleAndArtists() {
        assertEquals("First Song Artist One, Artist Two", SpotifyTrackQuery.build(" First Song ", " Artist One, Artist Two "))
    }

    @Test
    fun omitsBlankArtistText() {
        assertEquals("First Song", SpotifyTrackQuery.build("First Song", " "))
    }

    @Test
    fun omitsBlankTitle() {
        assertEquals("Only Artists", SpotifyTrackQuery.build("", "Only Artists"))
    }

    @Test
    fun returnsBlankWhenBothPartsBlank() {
        assertEquals("", SpotifyTrackQuery.build("  ", ""))
    }

    @Test
    fun stripsTrailingFromAnnotation() {
        assertEquals(
            "Vaama Vaama Thaman S, Dhanush",
            SpotifyTrackQuery.build("""Vaama Vaama - From "Idhayam Murali"""", "Thaman S, Dhanush"),
        )
    }

    @Test
    fun stripsParenthesisedFromAnnotation() {
        assertEquals(
            "Hangova Anirudh Ravichander",
            SpotifyTrackQuery.build("""Hangova (From "DC")""", "Anirudh Ravichander"),
        )
    }

    @Test
    fun collapsesLeftoverWhitespace() {
        assertEquals("Clean Title", SpotifyTrackQuery.build("""Clean   Title  """, " "))
    }
}
