/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package com.dumptruckman.minecraft.pluginbase.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import javax.annotation.Nullable;

public class Blocks {

    private Blocks() {
        throw new AssertionError();
    }

    public static BlockLocation getLocation(Block block) {
        return new BlockLocation(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
    }

    public static BlockLocation getLocation(Location loc) {
        return new BlockLocation(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public static BlockLocation getLocation(World world, int x, int y, int z) {
        return new BlockLocation(world.getName(), x, y, z);
    }

    @Nullable
    public static Block getBlockAt(BlockLocation loc) {
        World world = getWorldFor(loc);
        if (world == null) {
            return null;
        }
        return world.getBlockAt(loc.getX(), loc.getY(), loc.getZ());
    }

    public static Location getLocation(BlockLocation loc) {
        World world = getWorldFor(loc);
        if (world == null) {
            return null;
        }
        return new Location(world, loc.getX(), loc.getY(), loc.getZ());
    }

    @Nullable
    public static World getWorldFor(BlockLocation loc) {
        return Bukkit.getWorld(loc.getWorld());
    }
}
