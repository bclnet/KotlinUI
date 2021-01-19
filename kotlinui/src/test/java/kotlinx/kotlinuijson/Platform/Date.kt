package kotlinx.kotlinui

import android.icu.util.DateInterval
import io.mockk.*
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import java.util.*

class DateTest {
    @Test
    fun serialize() {
        val json = Json {
            prettyPrint = true
        }

        // date
        val orig_s0 = Date()
        val data_s0 = json.encodeToString(DateSerializer, orig_s0)
        val json_s0 = json.decodeFromString(DateSerializer, data_s0)
        Assert.assertEquals(orig_s0, json_s0)

        // dateinterval
        val dateInterval = mockk<DateInterval>()
        every { dateInterval.getFromDate() } returns 0
        every { dateInterval.getToDate() } returns 1
        val orig_s1 = dateInterval
        val data_s1 = json.encodeToString(DateIntervalSerializer, orig_s1)
        val json_s1 = json.decodeFromString(DateIntervalSerializer, data_s1)
//        Assert.assertEquals(orig_s1.fromDate, json_s1.fromDate)
//        Assert.assertEquals(orig_s1.toDate, json_s1.toDate)
    }
}