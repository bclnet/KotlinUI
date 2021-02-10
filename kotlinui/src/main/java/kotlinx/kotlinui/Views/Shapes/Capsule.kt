package kotlinx.kotlinui

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.graphics.Color as XColor
import android.view.View as XView

import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Capsule.Serializer::class)
data class Capsule(
    val style: RoundedCornerStyle = RoundedCornerStyle.circular
) : InsettableShape, ViewBuildable {
    override fun path(rect: Rect): Path = error("Never")
    override fun inset(by: Float): View = modifier(_Inset(by))
    override val body: View
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<Capsule> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":Capsule") {
                element("style", RoundedCornerStyle.Serializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: Capsule) =
            encoder.encodeStructure(descriptor) {
                if (value.style != RoundedCornerStyle.circular) encodeSerializableElement(descriptor, 0, RoundedCornerStyle.Serializer, value.style)
            }

        override fun deserialize(decoder: Decoder): Capsule =
            decoder.decodeStructure(descriptor) {
                var style: RoundedCornerStyle = RoundedCornerStyle.circular
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> style = decodeSerializableElement(descriptor, 0, RoundedCornerStyle.Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Capsule(style)
            }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        val view = XView(context)
        val drawable = GradientDrawable()
        drawable.setColor(XColor.parseColor("#ff0000"))
        drawable.shape = GradientDrawable.OVAL
        drawable.setStroke(5, XColor.parseColor("#03dc13"))
        view.background = drawable
        return view
    }

    @Serializable(with = _Inset.Serializer::class)
    data class _Inset(
        val amount: Float
    ) : ViewModifier {
        //: Codable
        internal object Serializer : KSerializer<_Inset> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor(":Capsule._Inset") {
                    element<Float>("amount")
                }

            override fun serialize(encoder: Encoder, value: _Inset) =
                encoder.encodeStructure(descriptor) {
                    encodeFloatElement(descriptor, 0, value.amount)
                }

            override fun deserialize(decoder: Decoder): _Inset =
                decoder.decodeStructure(descriptor) {
                    var amount = 0f
                    while (true) {
                        when (val index = decodeElementIndex(descriptor)) {
                            0 -> amount = decodeFloatElement(descriptor, 0)
                            CompositeDecoder.DECODE_DONE -> break
                            else -> error("Unexpected index: $index")
                        }
                    }
                    _Inset(amount)
                }
        }
    }

    companion object {
        //: Register
        fun register() {
            PType.register<Capsule>()
            PType.register<_Inset>()
        }
    }
}
