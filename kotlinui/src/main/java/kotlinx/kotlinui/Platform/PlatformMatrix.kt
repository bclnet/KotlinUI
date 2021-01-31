@file: OptIn(ExperimentalSerializationApi::class)
package kotlinx.kotlinui

import android.graphics.Matrix
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

@Serializer(forClass = Matrix::class)
object MatrixSerializer : KSerializer<Matrix> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Matrix") {
        }

    override fun serialize(encoder: Encoder, value: Matrix) =
        encoder.encodeStructure(descriptor) {
            error("Not Implemented")
        }

    override fun deserialize(decoder: Decoder): Matrix =
        decoder.decodeStructure(descriptor) {
            error("Not Implemented")
        }
}