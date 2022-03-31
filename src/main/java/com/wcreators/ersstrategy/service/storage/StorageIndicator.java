package com.wcreators.ersstrategy.service.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class StorageIndicator<T, E> {

    protected final List<E> points = new ArrayList<>();

    public E addPoint(T value) {
        E point = calculate(value);
        addPointWithoutCalc(point);
        return point;
    }

    public void addPointWithoutCalc(E point) {
        points.add(point);
        if (points.size() > 150) {
            points.remove(0);
        }
    }

    protected abstract E calculate(T value);

    protected boolean isEmpty() {
        return points.isEmpty();
    }

    public Optional<E> lastAdded() {
        if (isEmpty()) {
          return Optional.empty();
        }
        return Optional.of(points.get(points.size() - 1));
    }

    public int size() {
        return points.size();
    }

    public E get(int index) {
        return points.get(index);
    }

}
