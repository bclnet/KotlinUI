package kotlinx.kotlinui

data class ViewDimensions(
    var width: Float = 0f,
    var height: Float = 0f
) {
    operator fun get(guide: HorizontalAlignment): Float = 0f
    operator fun get(guide: VerticalAlignment): Float = 0f
}
