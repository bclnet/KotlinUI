package kotlinx.kotlinui

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.EnumSet

@Serializable(with = EdgeSerializer::class)
enum class Edge(val rawValue: Byte) {
    top(1),
    leading(2),
    bottom(4),
    trailing(8);

    object Set {
        @Serializable(with = EdgeSetSerializer::class)
        var top: EnumSet<Edge> = EnumSet.of(Edge.top)

        @Serializable(with = EdgeSetSerializer::class)
        var leading: EnumSet<Edge> = EnumSet.of(Edge.leading)

        @Serializable(with = EdgeSetSerializer::class)
        var bottom: EnumSet<Edge> = EnumSet.of(Edge.bottom)

        @Serializable(with = EdgeSetSerializer::class)
        var trailing: EnumSet<Edge> = EnumSet.of(Edge.trailing)

        @Serializable(with = EdgeSetSerializer::class)
        var horizontal: EnumSet<Edge> = EnumSet.of(Edge.leading, Edge.trailing)

        @Serializable(with = EdgeSetSerializer::class)
        var vertical: EnumSet<Edge> = EnumSet.of(Edge.top, Edge.bottom)

        @Serializable(with = EdgeSetSerializer::class)
        var all: EnumSet<Edge> = EnumSet.allOf(Edge::class.java)
    }

    internal object EdgeSetSerializer : KSerializer<EnumSet<Edge>> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Edge.Set", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: EnumSet<Edge>) {
            when (value) {
                else -> error("$value")
            }
        }

        override fun deserialize(decoder: Decoder): EnumSet<Edge> {
            val value = decoder.decodeString()
            when (value) {
                else -> error(value)
            }
        }
    }
}

internal object EdgeSerializer : KSerializer<Edge> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Edge", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Edge) {
        when (value) {
            Edge.top -> encoder.encodeString("top")
            Edge.leading -> encoder.encodeString("leading")
            Edge.bottom -> encoder.encodeString("bottom")
            Edge.trailing -> encoder.encodeString("trailing")
        }
    }

    override fun deserialize(decoder: Decoder): Edge =
        when (val value = decoder.decodeString()) {
            "top" -> Edge.top
            "leading" -> Edge.leading
            "bottom" -> Edge.bottom
            "trailing" -> Edge.trailing
            else -> error(value)
        }
}

@Serializable(with = EdgeInsetsSerializer::class)
class EdgeInsets constructor(
    var top: Float = 0f,
    var leading: Float = 0f,
    var bottom: Float = 0f,
    var trailing: Float = 0f
) {
    constructor(all: Float) : this(all, all, all, all)

    override fun equals(other: Any?): Boolean {
        if (other !is EdgeInsets) return false
        return top == other.top &&
            leading == other.leading &&
            bottom == other.bottom &&
            trailing == other.trailing
    }

    override fun hashCode(): Int {
        var result = top.hashCode()
        result = 31 * result + leading.hashCode()
        result = 31 * result + bottom.hashCode()
        result = 31 * result + trailing.hashCode()
        return result
    }
}

internal object EdgeInsetsSerializer : KSerializer<EdgeInsets> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("EdgeInsets") {
            element<Float>("top")
            element<Float>("leading")
            element<Float>("bottom")
            element<Float>("trailing")
        }

    override fun serialize(encoder: Encoder, value: EdgeInsets) =
        encoder.encodeStructure(descriptor) {
            encodeFloatElement(descriptor, 0, value.top)
            encodeFloatElement(descriptor, 1, value.leading)
            encodeFloatElement(descriptor, 2, value.bottom)
            encodeFloatElement(descriptor, 3, value.trailing)
        }

    override fun deserialize(decoder: Decoder): EdgeInsets =
        decoder.decodeStructure(descriptor) {
            EdgeInsets(
                decodeFloatElement(descriptor, 0),
                decodeFloatElement(descriptor, 1),
                decodeFloatElement(descriptor, 2),
                decodeFloatElement(descriptor, 3)
            )
        }

    //    override fun deserialize(decoder: Decoder): EdgeInsets =
//        decoder.decodeStructure(descriptor) {
//            var top = 0f
//            var leading = 0f
//            var bottom = 0f
//            var trailing = 0f
//            while (true) {
//                when (val index = decodeElementIndex(descriptor)) {
//                    0 -> top = decodeFloatElement(descriptor, 0)
//                    1 -> leading = decodeFloatElement(descriptor, 1)
//                    2 -> bottom = decodeFloatElement(descriptor, 2)
//                    3 -> trailing = decodeFloatElement(descriptor, 3)
//                    CompositeDecoder.DECODE_DONE -> break
//                    else -> error("Unexpected index: $index")
//                }
//            }
//            EdgeInsets(top, leading, bottom, trailing)
//        }
}