@file:OptIn(ExperimentalStdlibApi::class)

package kotlinx.kotlinui

import android.content.Context
import android.widget.TextView
import android.view.View as XView
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Divider.Serializer::class)
class Divider : View, ViewBuildable {
    override val body: Never
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<Divider> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor("Divider") {
            }

        override fun serialize(encoder: Encoder, value: Divider) =
            encoder.encodeStructure(descriptor) {
            }

        override fun deserialize(decoder: Decoder): Divider =
            decoder.decodeStructure(descriptor) {
                Divider()
            }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        val view = TextView(context)
        return view
    }

    companion object {
        //: Register
        fun register() {
            PType.register<Divider>()
        }
    }
}

//internal fun Divider._makeView(view: _GraphValue<Divider>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
