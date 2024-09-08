package me.definedoddy.toolkit.types;

public class EnumFlag<T extends Enum<T>> {
    private int value;

    @SafeVarargs
    public EnumFlag(T... flags) {
        for (T flag : flags) add(flag);
    }

    public void add(T flag) {
        value |= 1 << flag.ordinal();
    }

    public void remove(T flag) {
        value &= ~(1 << flag.ordinal());
    }

    public boolean has(T flag) {
        return (value & (1 << flag.ordinal())) != 0;
    }

    public void clear() {
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
