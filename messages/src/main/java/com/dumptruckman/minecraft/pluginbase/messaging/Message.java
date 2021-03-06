/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package com.dumptruckman.minecraft.pluginbase.messaging;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A localization key and its defaults.
 *
 * The key represents the location of the localized strings in a language file.
 * The default is what should populate the localization file by default.
 */
public final class Message {

    @NotNull
    private final String def;
    @Nullable
    private final String key;

    /**
     * Creates a new localization message.
     *
     * These messages are automatically added to the localization pool which is generally written out to a default
     * language file.  This means that this Message needs to be initialized prior to the default language file being
     * generated.
     *
     * @param key The localization key for this message.
     * @param def The default message.
     */
    public Message(@NotNull final String key, @NotNull final String def) {
        this.key = key;
        this.def = def;
        Messages.registerMessage(this);
    }

    Message(@NotNull final String def) {
        this.key = null;
        this.def = def;
        Messages.registerMessage(this);
    }

    /**
     * The default messages in whatever your pimary plugin language is.
     *
     * @return The default non-localized messages.
     */
    @NotNull
    public String getDefault() {
        return def;
    }

    /**
     * The localization key for the message.
     *
     * @return The localization key for the message.
     */
    @Nullable
    public String getKey() {
        return key;
    }
}
