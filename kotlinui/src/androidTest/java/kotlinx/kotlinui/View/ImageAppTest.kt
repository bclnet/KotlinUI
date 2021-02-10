package kotlinx.kotlinui

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ImageAppTest {
    lateinit var activityRule: ActivityScenario<TestActivity>

    @Test
    fun sample() {
        TestActivity.view = Image("Target")
            .buildView(ApplicationProvider.getApplicationContext())
        activityRule = ActivityScenario.launch(TestActivity::class.java)
        Espresso.onView(ViewMatchers.withTagKey(1))
            .perform(ViewActions.click())
    }
}