package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var usedWords = mutableListOf<String>()
    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    /**
     * Gets a random word for the list of words and shuffles the letters in it, then return the shuffled word
     * a list of words that have been used [usedWords] is adapted as the values of [_currentScrambledWord]
     * and [_currentWordCount]
     */
    private fun getNextWord()
    {
        currentWord = allWordsList.random()
        while (usedWords.contains(currentWord))
            currentWord = allWordsList.random()
        usedWords.add(currentWord)

        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (currentWord == String(tempWord))
            tempWord.shuffle()

        _currentScrambledWord = String(tempWord)
        ++_currentWordCount
    }

    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS.
    * Updates the next word.
    */
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        usedWords.clear()
        getNextWord()
    }

}