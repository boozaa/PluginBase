/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package com.dumptruckman.minecraft.pluginbase.properties;

import com.dumptruckman.minecraft.pluginbase.logging.Logging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class DefaultStringSerializer<T> implements PropertySerializer<T> {

    private final Method valueOfMethod;
    private final Class<T> clazz;
    private final boolean isEnum;

    DefaultStringSerializer(Class<T> clazz) {
        this.clazz = clazz;
        try {
            valueOfMethod = clazz.getMethod("valueOf", String.class);
            valueOfMethod.setAccessible(true);
            if (!valueOfMethod.getReturnType().equals(clazz) || !Modifier.isStatic(valueOfMethod.getModifiers())) {
                throw new IllegalArgumentException(clazz.getName() + " has no static valueOf(String) method!");
            }
            if (Enum.class.isAssignableFrom(clazz)) {
                isEnum = true;
            } else {
                isEnum = false;
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(clazz.getName() + " has no static valueOf(String) method!");
        }
    }

    @Override
    public T deserialize(Object obj) {
        try {
            return clazz.cast(valueOfMethod.invoke(null, !isEnum ? obj.toString() : obj.toString().toUpperCase()));
        } catch (IllegalAccessException e) {
            Logging.severe(this.clazz.getName() + " has no accessible static valueOf(String) method!");
        } catch (InvocationTargetException e) {
            Logging.severe(this.clazz.getName() + ".valueOf(String) is throwing an exception:");
            e.printStackTrace();
        }
        throw new IllegalStateException(this.getClass().getName() + " was used illegally!  Contact dumptruckman!");
    }

    @Override
    public Object serialize(T t) {
        return t.toString();
    }
}
