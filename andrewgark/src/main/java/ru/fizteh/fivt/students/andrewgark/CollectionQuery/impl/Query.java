package ru.fizteh.fivt.students.andrewgark.CollectionQuery.impl;

import java.lang.reflect.InvocationTargetException;

public interface Query<R> {
    Iterable<R> execute() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException;
}