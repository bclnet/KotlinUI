package kotlinx.kotlinui

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.io.ByteArrayOutputStream
import android.graphics.drawable.Drawable as UXImage
import android.media.Image as CGImage

// https://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
internal object UXImageSerializer : KSerializer<UXImage> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("UXImage", serializer<ByteArray>().descriptor)

    override fun serialize(encoder: Encoder, value: UXImage) {
        val bitmap = if (value is BitmapDrawable) value.bitmap
        else {
            val bitmap = if (value.intrinsicWidth <= 0 || value.intrinsicHeight <= 0) Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            else Bitmap.createBitmap(value.intrinsicWidth, value.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            value.setBounds(0, 0, canvas.width, canvas.height)
            value.draw(canvas)
            bitmap
        }
        return CGImageSerializer.serialize(encoder, bitmap as CGImage)
    }

    override fun deserialize(decoder: Decoder): UXImage =
        BitmapDrawable(Resources.getSystem(), CGImageSerializer.deserialize(decoder) as Bitmap) as UXImage
}

// https://stackoverflow.com/questions/41775968/how-to-convert-android-media-image-to-bitmap-object
// https://stackoverflow.com/questions/9219023/best-way-to-serialize-deserialize-an-image-in-android
internal object CGImageSerializer : KSerializer<CGImage> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CGImage", serializer<ByteArray>().descriptor)

    override fun serialize(encoder: Encoder, value: CGImage) {
        if (value.planes.isEmpty()) {
            encoder.encodeSerializableValue(serializer(), ByteArray(0))
            return
        }
        val buffer = value.planes[0].buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        val bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val stream = ByteArrayOutputStream()
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        val data = stream.toByteArray()
        encoder.encodeSerializableValue(serializer(), data)
    }

    override fun deserialize(decoder: Decoder): CGImage =
        decoder.decodeStructure(descriptor) {
            val data = decoder.decodeSerializableValue(serializer<ByteArray>())
            if (data.isEmpty())
                return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) as CGImage
            val image = BitmapFactory.decodeByteArray(data, 0, data.size)
            return image as CGImage
        }
}