package com.example.rxretrofitpractice.dataClass

data class SeriesInfo(
    val bookDisplayNumber: String,
    val kind: String,
    val volumeSeries: List<VolumeSery>
)