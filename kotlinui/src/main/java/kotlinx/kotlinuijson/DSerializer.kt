package kotlinx.kotlinuijson

import kotlinx.kotlinuijson.DynaType
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlin.reflect.KClass

//@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.TYPE)
//annotation class DSerializable(
//    val with: KClass<out DSerializer<*>>
//)
//
//interface DSerializer<T> : SerializationStrategy<T>, DynaDeserializationStrategy<T> {
//    override val descriptor: SerialDescriptor
//}
//
//interface DynaDeserializationStrategy<T> {
//    val descriptor: SerialDescriptor
//    fun deserialize(decoder: Decoder, dynaType: DynaType): T
//}
//
//
//fun <T : Any?> decodeDSerializableElement(
//    descriptor: SerialDescriptor,
//    index: Int,
//    deserializer: DeserializationStrategy<T>,
//    previousValue: T? = null
//): T {
//    error("")
//}