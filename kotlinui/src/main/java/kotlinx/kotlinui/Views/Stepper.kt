package kotlinx.kotlinui

import kotlinx.ptype.PType

data class Stepper<Label : View> private constructor(
    val onIncrement: (() -> Unit)?,
    val onDecrement: (() -> Unit)?,
    val onEditingChanged: (Boolean) -> Unit = {},
    val label: Label
) : View {
    constructor(
        onIncrement: (() -> Unit)?,
        onDecrement: (() -> Unit)?,
        onEditingChanged: (Boolean) -> Unit = {},
        label: () -> Label
    ) : this(onIncrement, onDecrement, onEditingChanged, label())

//    constructor init<V>(value: Binding<V>, step: V.Stride = 1, onEditingChanged: @escaping (Bool) -> Void = { _ in }, @ViewBuilder label: () -> Label) where V: Strideable {
//        self.label = label()
//        self.onEditingChanged = onEditingChanged
//    }
//
//    public init<V>(value: Binding<V>, in bounds: ClosedRange<V>, step: V.Stride = 1, onEditingChanged: @escaping (Bool) -> Void = { _ in }, @ViewBuilder label: () -> Label) where V: Strideable {
//        self.label = label()
//        self.onEditingChanged = onEditingChanged
//    }

    override val body: Label
        get() = label

    companion object {
        //: Register
        fun register() {
//            PType.register<Stepper<EmptyView>>()
            PType.register<Stepper<AnyView>>()
        }
    }
}

//
//extension Stepper where Label == Text {
//    public init(_ titleKey: LocalizedStringKey, onIncrement: (() -> Void)?, onDecrement: (() -> Void)?, onEditingChanged: @escaping (Bool) -> Void = { _ in }) {
//        self.label = Text(titleKey)
//        self.onIncrement = onIncrement
//        self.onDecrement = onDecrement
//        self.onEditingChanged = onEditingChanged
//    }
//
//    public init<S>(_ title: S, onIncrement: (() -> Void)?, onDecrement: (() -> Void)?, onEditingChanged: @escaping (Bool) -> Void = { _ in }) where S: StringProtocol {
//        self.label = Text(title)
//        self.onIncrement = onIncrement
//        self.onDecrement = onDecrement
//        self.onEditingChanged = onEditingChanged
//    }
//
//    public init<V>(_ titleKey: LocalizedStringKey, value: Binding<V>, step: V.Stride = 1, onEditingChanged: @escaping (Bool) -> Void = { _ in }) where V: Strideable {
//        self.label = Text(titleKey)
//        self.onEditingChanged = onEditingChanged
//    }
//
//   public init<S, V>(_ title: S, value: Binding<V>, step: V.Stride = 1, onEditingChanged: @escaping (Bool) -> Void = { _ in }) where S: StringProtocol, V: Strideable {
//        self.label = Text(title)
//        self.onEditingChanged = onEditingChanged
//    }
//
//    public init<V>(_ titleKey: LocalizedStringKey, value: Binding<V>, in bounds: ClosedRange<V>, step: V.Stride = 1, onEditingChanged: @escaping (Bool) -> Void = { _ in }) where V: Strideable {
//        self.label = Text(titleKey)
//        self.onEditingChanged = onEditingChanged
//    }
//
//    public init<S, V>(_ title: S, value: Binding<V>, in bounds: ClosedRange<V>, step: V.Stride = 1, onEditingChanged: @escaping (Bool) -> Void = { _ in }) where S: StringProtocol, V: Strideable {
//        self.label = Text(title)
//        self.onEditingChanged = onEditingChanged
//    }
//}

