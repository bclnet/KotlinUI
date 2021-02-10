package kotlinx.kotlinui

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.View as XView
import kotlinx.ptype.PType
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(with = Circle.Serializer::class)
class Circle : InsettableShape, ViewBuildable {
    override fun equals(other: Any?): Boolean = other is Circle
    override fun hashCode(): Int = javaClass.hashCode()

    override fun path(rect: Rect): Path = error("Never")
    override fun inset(by: Float): View = modifier(_Inset(by))
    override val body: View
        get() = error("Never")

    //: Codable
    internal object Serializer : KSerializer<Circle> {
        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":Circle") { }

        override fun serialize(encoder: Encoder, value: Circle) =
            encoder.encodeStructure(descriptor) { }

        override fun deserialize(decoder: Decoder): Circle =
            decoder.decodeStructure(descriptor) { Circle() }
    }

    //: ViewBuildable
    override fun buildView(context: Context?): XView {
        val view = XView(context)
        val drawable = GradientDrawable()
        drawable.setColor(Color.parseColor("#ff0000"))
        drawable.shape = GradientDrawable.OVAL
        drawable.setStroke(5, Color.parseColor("#03dc13"))
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
                buildClassSerialDescriptor(":Circle._Inset") {
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
            PType.register<Circle>()
            PType.register<_Inset>()
        }
    }
}