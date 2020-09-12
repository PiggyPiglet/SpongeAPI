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

import org.spongepowered.api.world.volume.MutableVolume;
import org.spongepowered.api.world.volume.Volume;
import org.spongepowered.math.vector.Vector3i;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface VolumeStream<V extends Volume, T> {

    V getVolume();

    VolumeStream<V, T> filterPosition(Predicate<Vector3i> predicate);

    VolumeStream<V, T> filter(VolumePredicate<V, T> predicate);

    default VolumeStream<V, T> filter(final Predicate<VolumeElement<V, ? super T>> predicate) {
        return this.filter((volume, element, x, y, z) -> predicate.test(VolumeElement.of(volume, element, new Vector3i(x, y, z))));
    }

    VolumeStream<V, T> map(VolumeMapper<V, T> mapper);

    default VolumeStream<V, T> map(final Function<VolumeElement<V, T>, ? extends T> mapper) {
        return this.map(((volume, value, x, y, z) -> mapper.apply(VolumeElement.of(volume, value, new Vector3i(x, y, z)))));
    }

    long count();

    boolean allMatch(VolumePredicate<V, ? super T> predicate);

    default boolean allMatch(final Predicate<VolumeElement<V, ? super T>> predicate) {
        return this.allMatch(((volume, element, x, y, z) -> predicate.test(VolumeElement.of(volume, element, new Vector3i(x, y, z)))));
    }

    boolean noneMatch(VolumePredicate<V, ? super T> predicate);

    boolean noneMatch(Predicate<VolumeElement<V, ? super T>> predicate);

    boolean anyMatch(VolumePredicate<V, ? super T> predicate);

    boolean anyMatch(Predicate<VolumeElement<V, ? super T>> predicate);

    Optional<VolumeElement<V, T>> findFirst();

    Optional<VolumeElement<V, T>> findAny();

    Stream<VolumeElement<V, T>> toStream();

    <W extends MutableVolume> void apply(VolumeCollector<W, T, ?> collector);

    <W extends MutableVolume, R> void applyUntil(VolumeCollector<W, T, R> collector, Predicate<R> predicate);

    void forEach(VolumeConsumer<V, T> visitor);

    void forEach(Consumer<VolumeElement<V, T>> consumer);

}
