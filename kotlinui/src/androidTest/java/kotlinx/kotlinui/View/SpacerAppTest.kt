package kotlinx.kotlinui

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class SpacerAppTest {
    lateinit var activityRule: ActivityScenario<TestActivity>

    @Test
    fun sample() {
        TestActivity.view = Spacer()
            .buildView(ApplicationProvider.getApplicationContext())
        activityRule = ActivityScenario.launch(TestActivity::class.java)
    }
}