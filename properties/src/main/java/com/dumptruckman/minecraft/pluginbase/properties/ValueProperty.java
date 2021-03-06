/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package com.dumptruckman.minecraft.pluginbase.properties;

import com.dumptruckman.minecraft.pluginbase.messaging.Message;

import java.util.List;

public interface ValueProperty<T> extends Property<T> {

    /**
     * Retrieves the default value for a config path.
     *
     * @return The default value for a config path.
     */
    Object getDefault();

    /**
     * Retrieves the aliases for this entry.
     *
     * Aliases are sometimes used when config entries are checked reflectively to offer an easy way to set entry
     * values via command.
     *
     * @return The aliases for this entry.
     */
    List<String> getAliases();

    /**
     * Verifies that the given object is a valid value for this entry.
     *
     * @param obj The object to verify.
     * @return True if the object is a valid value for this entry.
     */
    boolean isValid(T obj);

    /**
     * Indicates whether ot not this entry is deprecated which will cause it to no longer default itself in the config
     * if missing.
     *
     * @return True if entry is deprecated.
     */
    boolean isDeprecated();

    /**
     * This defines whether or not the value should be defaulted when retrieved if the value is missing from the config.
     * This does not, however, cause the value to be add to the config.  It will simply cause the default to be
     * returned.
     *
     * @return True if value should be defaulted.
     */
    boolean shouldDefaultIfMissing();

    /**
     * Gets the default serializer for this property if there is one.
     *
     * This will be used to serialize/deserialize the property if not replaced by the {@link Properties} implementation.
     *
     * @return The default serializer for this property or null if not specified.
     */
    PropertySerializer<T> getDefaultSerializer();

    /**
     * Retrieves the language to use when an invalid value is attempted to be used for this entry.
     * This will return {@link com.dumptruckman.minecraft.pluginbase.messaging.Messages#BLANK} by default.
     *
     * @return the language to use when an invalid value is attempted to be used for this entry.
     */
    Message getInvalidMessage();

    /**
     * Retrieves the description of this entry in Message form.
     * This will return {@link com.dumptruckman.minecraft.pluginbase.messaging.Messages#BLANK} by default.
     *
     * @return the description of this entry in Message form.
     */
    Message getDescription();
}
