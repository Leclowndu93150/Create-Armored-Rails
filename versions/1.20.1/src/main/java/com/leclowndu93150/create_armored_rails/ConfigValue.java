package com.leclowndu93150.create_armored_rails;

import java.util.function.Supplier;

public class ConfigValue<T> {
    private final Supplier<T> getter;

    public ConfigValue(Supplier<T> getter) {
        this.getter = getter;
    }

    public T get() {
        return getter.get();
    }
}
