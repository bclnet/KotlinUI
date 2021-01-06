package kotlinx.kotlinui

import java.util.EnumSet

enum class Edge(val rawValue: Byte) {
    top(1),
    leading(2),
    bottom(4),
    trailing(8);

    object Set {
        var top: EnumSet<Edge> = EnumSet.of(Edge.top)
        var leading: EnumSet<Edge> = EnumSet.of(Edge.leading)
        var bottom: EnumSet<Edge> = EnumSet.of(Edge.bottom)
        var trailing: EnumSet<Edge> = EnumSet.of(Edge.trailing)
        var horizontal: EnumSet<Edge> = EnumSet.of(Edge.leading, Edge.trailing)
        var vertical: EnumSet<Edge> = EnumSet.of(Edge.top, Edge.bottom)
        var all: EnumSet<Edge> = EnumSet.allOf(Edge::class.java)
    }
}
