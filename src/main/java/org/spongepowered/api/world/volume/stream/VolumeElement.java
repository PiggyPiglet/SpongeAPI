/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.world.volume.stream;

import org.spongepowered.api.world.volume.Volume;
import org.spongepowered.math.vector.Vector3i;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.function.Supplier;

public final class VolumeElement<V extends Volume, T> {

    public static <V extends Volume, T> VolumeElement<V, T> of(final V volume, final Supplier<? extends T> type, final Vector3i position) {
        final WeakReference<V> volumeRef = new WeakReference<>(volume);
        final Supplier<V> volumeSupplier = () -> Objects.requireNonNull(volumeRef.get(), "Volume de-referenced");
        return new VolumeElement<>(
            Objects.requireNonNull(volumeSupplier, "volume"),
            Objects.requireNonNull(type, "type"),
            Objects.requireNonNull(position, "position")
        );
    }

    public static <V extends Volume, T> VolumeElement<V, T> of(final V volume, final T type, final Vector3i position) {
        final WeakReference<V> volumeRef = new WeakReference<>(volume);
        final Supplier<V> volumeSupplier = () -> Objects.requireNonNull(volumeRef.get(), "Volume de-referenced");
        final WeakReference<T> typeRef = new WeakReference<>(type);
        final Supplier<T> typeSupplier = () -> Objects.requireNonNull(typeRef.get(), "Element instance de-referenced");
        return new VolumeElement<>(
            Objects.requireNonNull(volumeSupplier, "volume"),
            Objects.requireNonNull(typeSupplier, "type"),
            Objects.requireNonNull(position, "position")
        );
    }

    private final Supplier<? extends V> reference;
    private final Supplier<? extends T> type;
    private final Vector3i position;

    private VolumeElement(final Supplier<? extends V> volume, final Supplier<? extends T> type, final Vector3i position) {
        this.reference = volume;
        this.type = type;
        this.position = position;
    }

    public V getVolume() {
        return this.reference.get();
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public T getType() {
        return this.type.get();
    }

}
