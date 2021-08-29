package com.example.rxretrofitpractice.dataClass

data class PanelizationSummary(
    val containsEpubBubbles: Boolean,
    val containsImageBubbles: Boolean,
    val epubBubbleVersion: String,
    val imageBubbleVersion: String
)