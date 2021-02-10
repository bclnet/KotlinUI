package kotlinx.kotlinui

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextAppTest {
    lateinit var activityRule: ActivityScenario<TestActivity>

    @Test
    fun sample() {
        TestActivity.view = Text("Target Text")
            .buildView(ApplicationProvider.getApplicationContext())
        activityRule = ActivityScenario.launch(TestActivity::class.java)
        onView(withText("Target Text"))
            .perform(click())
    }
}