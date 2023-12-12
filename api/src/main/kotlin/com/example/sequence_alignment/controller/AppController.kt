package com.example.sequence_alignment.controller

import com.example.sequence_alignment.service.WordAnalyser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = ["*"])
class AppController(
    private val wordAnalyser: WordAnalyser
) {
    @GetMapping("/parse-text/{text}")
    fun parseText(@PathVariable text: String): Map<String, List<String>> {
        return wordAnalyser.parseText(text)
    }
}