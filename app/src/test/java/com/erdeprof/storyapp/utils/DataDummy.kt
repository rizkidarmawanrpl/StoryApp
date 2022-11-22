package com.erdeprof.storyapp.utils

import com.erdeprof.storyapp.dashboard.data.Story

object DataDummy {
    fun generateDummyStoriesEntity(): List<Story> {
        val storiesList = ArrayList<Story>()
        for (i in 0..100) {
            val stories = Story(
                "",
                "",
                "2022-11-21T22:22:22Z",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "Test test..",
                "Name $i",
                "$i"
            )
            storiesList.add(stories)
        }

        return storiesList;
    }
}