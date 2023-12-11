package com.example.sequence_alignment.service

import org.springframework.stereotype.Service

@Service
class LevenshteinAlgorithm {
    fun getDistance(word1: String, word2: String): Int {
        // create a matrix of size (word1.length + 1) * (word2.length + 1) with 0 in each cell
        val matrix = Array(word1.length + 1) { IntArray(word2.length + 1) }

        // Fill the first row with the cost of a gap
        for (i in 0..word1.length) {
            matrix[i][0] = i * 2
        }

        // Fill the first column with the cost of a gap
        for (j in 0..word2.length) {
            matrix[0][j] = j * 2
        }

        // Execute the algorithm, filling the matrix
        for (i in 1..word1.length) {
            for (j in 1..word2.length) {
                // The distance is the minimum of one of the three options:
                // 1. Gap in the first word (i.e. delete a character from the first word)
                // 2. Gap in the second word (i.e. insert a character into the second word)
                // 3. Mismatch (i.e. replace a character in the first word with a character from the second word)
                matrix[i][j] = minOf(
                    matrix[i - 1][j] + 3,
                    matrix[i][j - 1] + 5,
                    matrix[i - 1][j - 1] + if (word1[i - 1] == word2[j - 1]) 0 else 2
                )
            }
        }

        // Return the distance between the words, which is the last element of the matrix
        return matrix[word1.length][word2.length]
    }
}