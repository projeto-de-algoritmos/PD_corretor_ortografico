package com.example.sequence_alignment.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LevenshteinAlgorithmTest {
    private val levenshteinAlgorithm = LevenshteinAlgorithm()

    @Test
    fun `distance with same size words should work`() {
        // Arrange
        val word1 = "TACATG"
        val word2 = "CTACCG"

        // Act
        val distance = levenshteinAlgorithm.getDistance(word1, word2)

        // Assert
        assertThat(distance).isEqualTo(7)
    }

    @Test
    fun `distance with different size words should work`() {
        // Arrange
        val word1 = "kitten"
        val word2 = "sitting"

        // Act
        val distance = levenshteinAlgorithm.getDistance(word1, word2)

        // Assert
        assertThat(distance).isEqualTo(8)
    }

    @Test
    fun `distance with empty words should work`() {
        // Arrange
        val word1 = ""
        val word2 = ""

        // Act
        val distance = levenshteinAlgorithm.getDistance(word1, word2)

        // Assert
        assertThat(distance).isEqualTo(0)
    }

    @Test
    fun `distance with one empty word should work`() {
        // Arrange
        val word1 = "kitten"
        val word2 = ""

        // Act
        val distance = levenshteinAlgorithm.getDistance(word1, word2)

        // Assert
        assertThat(distance).isEqualTo(12)
    }
}