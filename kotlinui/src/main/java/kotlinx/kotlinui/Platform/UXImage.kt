package kotlinx.kotlinui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.io.ByteArrayOutputStream
import android.media.Image as UXImage

// https://stackoverflow.com/questions/41775968/how-to-convert-android-media-image-to-bitmap-object
// https://stackoverflow.com/questions/9219023/best-way-to-serialize-deserialize-an-image-in-android
internal object UXImageSerializer : KSerializer<UXImage> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UXImage", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UXImage) {
        val buffer = value.planes[0].buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        val bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val stream = ByteArrayOutputStream()
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        val data = stream.toByteArray()
        encoder.encodeSerializableValue(serializer(), data)
    }

    override fun deserialize(decoder: Decoder): UXImage =
        decoder.decodeStructure(descriptor) {
            val data = decoder.decodeSerializableValue(serializer<ByteArray>())
            val image = BitmapFactory.decodeByteArray(data, 0, data.size);
            return image as UXImage
        }
}