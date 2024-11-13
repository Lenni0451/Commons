package net.lenni0451.commons.collections;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@UtilityClass
public class Sets {

    /**
     * Sort a set using the given comparator.<br>
     * This will return a {@link LinkedHashSet} to keep the order of the set.
     *
     * @param set        The set to sort
     * @param comparator The comparator to use
     * @param <T>        The type of the set
     * @return The sorted set
     */
    public static <T> LinkedHashSet<T> sort(final Set<T> set, final Comparator<T> comparator) {
        return set.stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Merge multiple sets into one.<br>
     * The sets are not modified.
     *
     * @param set    The first set
     * @param others The other sets
     * @param <T>    The set type
     * @return The merged set
     */
    @SafeVarargs
    public static <T> Set<T> merge(final Set<T> set, final Set<T>... others) {
        Set<T> newSet = new HashSet<>(set);
        for (Set<T> other : others) newSet.addAll(other);
        return newSet;
    }

    /**
     * Create a new set with the given objects.
     *
     * @param setSupplier The supplier for the set
     * @param objects     The objects to add to the set
     * @param <T>         The set type
     * @param <O>         The object type
     * @return The created set
     */
    @SafeVarargs
    public static <T extends Set<O>, O> T any(final Supplier<T> setSupplier, final O... objects) {
        T set = setSupplier.get();
        Collections.addAll(set, objects);
        return set;
    }

    /**
     * Create a new set which is passed to the given consumer.
     *
     * @param setSupplier The supplier to create the set
     * @param setConsumer The consumer to pass the set to
     * @param <T>         The set type
     * @param <O>         The object type
     * @return The created set
     */
    public static <T extends Set<O>, O> T any(final Supplier<T> setSupplier, final Consumer<T> setConsumer) {
        T set = setSupplier.get();
        setConsumer.accept(set);
        return set;
    }

    /**
     * Create a new {@link HashSet} with the given objects.
     *
     * @param objects The objects to add to the set
     * @param <T>     The object type
     * @return The created set
     */
    @SafeVarargs
    public static <T> HashSet<T> hashSet(final T... objects) {
        return any(HashSet::new, objects);
    }

    /**
     * Create a new {@link HashSet} which is passed to the given consumer.
     *
     * @param setConsumer The consumer to pass the set to
     * @param <T>         The object type
     * @return The created set
     */
    public static <T> HashSet<T> hashSet(final Consumer<HashSet<T>> setConsumer) {
        return any(HashSet::new, setConsumer);
    }

    /**
     * Create a new {@link LinkedHashSet} with the given objects.
     *
     * @param objects The objects to add to the set
     * @param <T>     The object type
     * @return The created set
     */
    @SafeVarargs
    public static <T> LinkedHashSet<T> linkedHashSet(final T... objects) {
        return any(LinkedHashSet::new, objects);
    }

    /**
     * Create a new {@link LinkedHashSet} which is passed to the given consumer.
     *
     * @param setConsumer The consumer to pass the set to
     * @param <T>         The object type
     * @return The created set
     */
    public static <T> LinkedHashSet<T> linkedHashSet(final Consumer<LinkedHashSet<T>> setConsumer) {
        return any(LinkedHashSet::new, setConsumer);
    }

    /**
     * Create a new {@link ConcurrentSkipListSet} with the given objects.
     *
     * @param objects The objects to add to the set
     * @param <T>     The object type
     * @return The created set
     */
    @SafeVarargs
    public static <T> ConcurrentSkipListSet<T> concurrentSkipListSet(final T... objects) {
        return any(ConcurrentSkipListSet::new, objects);
    }

    /**
     * Create a new {@link ConcurrentSkipListSet} which is passed to the given consumer.
     *
     * @param setConsumer The consumer to pass the set to
     * @param <T>         The object type
     * @return The created set
     */
    public static <T> ConcurrentSkipListSet<T> concurrentSkipListSet(final Consumer<ConcurrentSkipListSet<T>> setConsumer) {
        return any(ConcurrentSkipListSet::new, setConsumer);
    }

    /**
     * <b>Deprecated</b> - Use {@link #hashSet(Object[])} instead.
     */
    @Deprecated
    @SafeVarargs
    @ApiStatus.ScheduledForRemoval
    public static <T> HashSet<T> newHashSet(final T... objects) {
        return hashSet(objects);
    }

    /**
     * <b>Deprecated</b> - Use {@link #hashSet(Consumer)} instead.
     */
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static <T> HashSet<T> newHashSet(final Consumer<HashSet<T>> setConsumer) {
        return hashSet(setConsumer);
    }

    /**
     * <b>Deprecated</b> - Use {@link #linkedHashSet(Object[])} instead.
     */
    @Deprecated
    @SafeVarargs
    @ApiStatus.ScheduledForRemoval
    public static <T> LinkedHashSet<T> newLinkedHashSet(final T... objects) {
        return linkedHashSet(objects);
    }

    /**
     * <b>Deprecated</b> - Use {@link #linkedHashSet(Consumer)} instead.
     */
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static <T> LinkedHashSet<T> newLinkedHashSet(final Consumer<LinkedHashSet<T>> setConsumer) {
        return linkedHashSet(setConsumer);
    }

    /**
     * <b>Deprecated</b> - Use {@link #concurrentSkipListSet(Object[])} instead.
     */
    @Deprecated
    @SafeVarargs
    @ApiStatus.ScheduledForRemoval
    public static <T> ConcurrentSkipListSet<T> newConcurrentSkipListSet(final T... objects) {
        return concurrentSkipListSet(objects);
    }

    /**
     * <b>Deprecated</b> - Use {@link #concurrentSkipListSet(Consumer)} instead.
     */
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static <T> ConcurrentSkipListSet<T> newConcurrentSkipListSet(final Consumer<ConcurrentSkipListSet<T>> setConsumer) {
        return concurrentSkipListSet(setConsumer);
    }

}
