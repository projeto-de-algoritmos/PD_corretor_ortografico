package com.example.sequence_alignment.service


import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class WordAnalyser(
    private val levenshteinAlgorithm: LevenshteinAlgorithm
) {
    // A tree to store all words from the dictionary
    private val dictionary = HashSet<String>()

    // Load the dictionary from the file into the tree
    init {
        val file = ClassPathResource("dictionary.txt").file
        file.forEachLine { dictionary.add(it) }
    }

    fun parseText(text: String): Map<String, List<String>> {
        val preprocessedWords = text
            // Convert all characters to lower case
            .lowercase()
            // Remove all characters except letters (brazilian alphabet) and spaces
            .replace(Regex("[^a-záàâãéèêíïóôõöúçñ ]"), "")
            // Remove all extra spaces
            .replace(Regex(" +"), " ")
            // Split the text into words
            .split(" ")

        // Save all words in a tree to remove duplicates
        val words = TreeSet(preprocessedWords)

        // Get 5 suggestions for each word, if there are any, and save them in a hash map (note the use of Threads)
        val result: MutableMap<String, List<String>> = words.parallelStream()
            .map { word -> word to suggestWord(word) }
            .filter { (_, suggestions) -> suggestions != null }
            .collect(Collectors.toMap({ it.first }, { it.second }))

        // Return the result
        return result
    }

    fun suggestWord(word: String): List<String>? {
        // If the word is in the dictionary, return an empty list
        if (dictionary.contains(word)) {
            return null
        }

        // Cache the distance between the word and the dictionary word to avoid calculating it twice
        val distances = HashMap<String, Int>(dictionary.size, 1f)

        // Use a tree to store the top 5 suggestions, the tree is sorted by the distance
        val suggestions = TreeSet<String>(compareBy { distances[it] })

        for (dictWord in dictionary) {
            // Compute the distance and save it in the cache
            val distance = levenshteinAlgorithm.getDistance(word, dictWord)
            distances[dictWord] = distance

            // If the tree have less than 5 suggestions, add the current word
            if (suggestions.size < 5) {
                suggestions.add(dictWord)
            }
            // If the current word has a smaller distance than the largest distance in the tree set,
            // remove the largest distance and add the current word
            else if (distance < distances[suggestions.last()]!!) {
                suggestions.pollLast()
                suggestions.add(dictWord)
            }
        }

        // Return the result
        return suggestions.toList()
    }
}
