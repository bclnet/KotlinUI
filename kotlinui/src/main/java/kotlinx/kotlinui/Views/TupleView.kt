package kotlinx.kotlinui

import kotlinx.ptype.PType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlinx.system.*
import kotlin.math.max

@Serializable(with = TupleView.Serializer::class)
data class TupleView<T>(
    val value: T
) : View {
    override val body: View
        get() = error("Never")

    internal class Serializer<T> : KSerializer<TupleView<T>> {
        val t0Serializer = PolymorphicSerializer(Any::class)
        val t1Serializer = PolymorphicSerializer(Any::class)
        val t2Serializer = PolymorphicSerializer(Any::class)
        val t3Serializer = PolymorphicSerializer(Any::class)
        val t4Serializer = PolymorphicSerializer(Any::class)
        val t5Serializer = PolymorphicSerializer(Any::class)
        val t6Serializer = PolymorphicSerializer(Any::class)
        val t7Serializer = PolymorphicSerializer(Any::class)
        val t8Serializer = PolymorphicSerializer(Any::class)
        val t9Serializer = PolymorphicSerializer(Any::class)

        override val descriptor: SerialDescriptor =
            buildClassSerialDescriptor(":TupleView") {
                element("0", t0Serializer.descriptor)
                element("1", t1Serializer.descriptor)
                element("2", t2Serializer.descriptor)
                element("3", t3Serializer.descriptor)
                element("4", t4Serializer.descriptor)
                element("5", t5Serializer.descriptor)
                element("6", t6Serializer.descriptor)
                element("7", t7Serializer.descriptor)
                element("8", t8Serializer.descriptor)
                element("9", t9Serializer.descriptor)
            }

        override fun serialize(encoder: Encoder, value: TupleView<T>) =
            encoder.encodeStructure(descriptor) {
                when (val v = value.value) {
                    is View -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v as Any)
                    }
                    is Tuple2<*, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                    }
                    is Tuple3<*, *, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                        encodeSerializableElement(descriptor, 2, t2Serializer, v.v2 as Any)
                    }
                    is Tuple4<*, *, *, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                        encodeSerializableElement(descriptor, 2, t2Serializer, v.v2 as Any)
                        encodeSerializableElement(descriptor, 3, t3Serializer, v.v3 as Any)
                    }
                    is Tuple5<*, *, *, *, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                        encodeSerializableElement(descriptor, 2, t2Serializer, v.v2 as Any)
                        encodeSerializableElement(descriptor, 3, t3Serializer, v.v3 as Any)
                        encodeSerializableElement(descriptor, 4, t4Serializer, v.v4 as Any)
                    }
                    is Tuple6<*, *, *, *, *, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                        encodeSerializableElement(descriptor, 2, t2Serializer, v.v2 as Any)
                        encodeSerializableElement(descriptor, 3, t3Serializer, v.v3 as Any)
                        encodeSerializableElement(descriptor, 4, t4Serializer, v.v4 as Any)
                        encodeSerializableElement(descriptor, 5, t5Serializer, v.v5 as Any)
                    }
                    is Tuple7<*, *, *, *, *, *, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                        encodeSerializableElement(descriptor, 2, t2Serializer, v.v2 as Any)
                        encodeSerializableElement(descriptor, 3, t3Serializer, v.v3 as Any)
                        encodeSerializableElement(descriptor, 4, t4Serializer, v.v4 as Any)
                        encodeSerializableElement(descriptor, 5, t5Serializer, v.v5 as Any)
                        encodeSerializableElement(descriptor, 6, t6Serializer, v.v6 as Any)
                    }
                    is Tuple8<*, *, *, *, *, *, *, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                        encodeSerializableElement(descriptor, 2, t2Serializer, v.v2 as Any)
                        encodeSerializableElement(descriptor, 3, t3Serializer, v.v3 as Any)
                        encodeSerializableElement(descriptor, 4, t4Serializer, v.v4 as Any)
                        encodeSerializableElement(descriptor, 5, t5Serializer, v.v5 as Any)
                        encodeSerializableElement(descriptor, 6, t6Serializer, v.v6 as Any)
                        encodeSerializableElement(descriptor, 7, t7Serializer, v.v7 as Any)
                    }
                    is Tuple9<*, *, *, *, *, *, *, *, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                        encodeSerializableElement(descriptor, 2, t2Serializer, v.v2 as Any)
                        encodeSerializableElement(descriptor, 3, t3Serializer, v.v3 as Any)
                        encodeSerializableElement(descriptor, 4, t4Serializer, v.v4 as Any)
                        encodeSerializableElement(descriptor, 5, t5Serializer, v.v5 as Any)
                        encodeSerializableElement(descriptor, 6, t6Serializer, v.v6 as Any)
                        encodeSerializableElement(descriptor, 7, t7Serializer, v.v7 as Any)
                        encodeSerializableElement(descriptor, 8, t8Serializer, v.v8 as Any)
                    }
                    is TupleA<*, *, *, *, *, *, *, *, *, *> -> {
                        encodeSerializableElement(descriptor, 0, t0Serializer, v.v0 as Any)
                        encodeSerializableElement(descriptor, 1, t1Serializer, v.v1 as Any)
                        encodeSerializableElement(descriptor, 2, t2Serializer, v.v2 as Any)
                        encodeSerializableElement(descriptor, 3, t3Serializer, v.v3 as Any)
                        encodeSerializableElement(descriptor, 4, t4Serializer, v.v4 as Any)
                        encodeSerializableElement(descriptor, 5, t5Serializer, v.v5 as Any)
                        encodeSerializableElement(descriptor, 6, t6Serializer, v.v6 as Any)
                        encodeSerializableElement(descriptor, 7, t7Serializer, v.v7 as Any)
                        encodeSerializableElement(descriptor, 8, t8Serializer, v.v8 as Any)
                        encodeSerializableElement(descriptor, 9, t9Serializer, v.v9 as Any)
                    }
                    else -> error("$v")
                }
            }

        override fun deserialize(decoder: Decoder): TupleView<T> =
            decoder.decodeStructure(descriptor) {
                var v0: Any? = null
                var v1: Any? = null
                var v2: Any? = null
                var v3: Any? = null
                var v4: Any? = null
                var v5: Any? = null
                var v6: Any? = null
                var v7: Any? = null
                var v8: Any? = null
                var v9: Any? = null
                var count = CompositeDecoder.DECODE_DONE
                while (true) {
                    val index = decodeElementIndex(descriptor)
                    count = max(count, index)
                    when (index) {
                        0 -> v0 = decodeSerializableElement(descriptor, 0, t0Serializer)
                        1 -> v1 = decodeSerializableElement(descriptor, 1, t1Serializer)
                        2 -> v2 = decodeSerializableElement(descriptor, 2, t2Serializer)
                        3 -> v3 = decodeSerializableElement(descriptor, 3, t3Serializer)
                        4 -> v4 = decodeSerializableElement(descriptor, 4, t4Serializer)
                        5 -> v5 = decodeSerializableElement(descriptor, 5, t5Serializer)
                        6 -> v6 = decodeSerializableElement(descriptor, 6, t6Serializer)
                        7 -> v7 = decodeSerializableElement(descriptor, 7, t7Serializer)
                        8 -> v8 = decodeSerializableElement(descriptor, 8, t8Serializer)
                        9 -> v9 = decodeSerializableElement(descriptor, 9, t9Serializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                when (count) {
                    0 -> TupleView(v0!! as T)
                    1 -> TupleView(Tuple2<Any, Any>(v0!!, v1!!) as T)
                    2 -> TupleView(Tuple3<Any, Any, Any>(v0!!, v1!!, v2!!) as T)
                    3 -> TupleView(Tuple4<Any, Any, Any, Any>(v0!!, v1!!, v2!!, v3!!) as T)
                    else -> error("$count")
                }
            }
    }


    companion object {
        //: Register
        fun register() {
            PType.register<TupleView<Any>>()
//            PType.register<Tuple2<View, View>>()
//            PType.register<Tuple3<View, View, View>>()
//            PType.register<Tuple4<View, View, View, View>>()
//            PType.register<Tuple5<View, View, View, View, View>>()
//            PType.register<Tuple6<View, View, View, View, View, View>>()
//            PType.register<Tuple7<View, View, View, View, View, View, View>>()
//            PType.register<Tuple8<View, View, View, View, View, View, View, View>>()
//            PType.register<Tuple9<View, View, View, View, View, View, View, View, View>>()
//            PType.register<TupleA<View, View, View, View, View, View, View, View, View, View>>()
        }
    }
}

//internal fun <T> TupleView<T>._makeView(view: _GraphValue<TupleView<T>>, inputs: _ViewInputs): _ViewOutputs = error("Not Implemented")
//internal fun <T> TupleView<T>._makeViewList(view: _GraphValue<TupleView<T>>, inputs: _ViewListInputs): _ViewListOutputs = error("Not Implemented")