package com.erdeprof.storyapp.utils

import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.login.data.User

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

    fun generateResultLogin(): User {
        val loginResult = User (
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUZKSC1mSS1OellBZ1BUeXYiLCJpYXQiOjE2Njk2OTE1NzV9.YT2QXwuneIWXWtw7_PyP8tVfONpnYESP1vhyrOMDXxM",
            name = "Rizki Darmawan",
            userId = "user-FJH-fI-NzYAgPTyv"
        )

        return loginResult
    }
}