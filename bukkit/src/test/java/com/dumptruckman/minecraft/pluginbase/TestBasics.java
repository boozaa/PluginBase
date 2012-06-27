/******************************************************************************
 * Multiverse 2 Copyright (c) the Multiverse Team 2011.                       *
 * Multiverse 2 is licensed under the BSD License.                            *
 * For more information please check the README.md file included              *
 * with this project.                                                         *
 ******************************************************************************/

package com.dumptruckman.minecraft.pluginbase;

import com.dumptruckman.minecraft.pluginbase.config.BaseConfig;
import com.dumptruckman.minecraft.pluginbase.plugin.AbstractBukkitPlugin;
import com.dumptruckman.minecraft.pluginbase.util.MockConfig;
import com.dumptruckman.minecraft.pluginbase.util.MockMessages;
import com.dumptruckman.minecraft.pluginbase.util.MockPlugin;
import com.dumptruckman.minecraft.pluginbase.util.MockQueuedCommand;
import com.dumptruckman.minecraft.pluginbase.util.TestInstanceCreator;
import junit.framework.Assert;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AbstractBukkitPlugin.class })
public class TestBasics {
    TestInstanceCreator creator;
    Server mockServer;
    CommandSender mockCommandSender;

    @Before
    public void setUp() throws Exception {
        creator = new TestInstanceCreator();
        assertTrue(creator.setUp());
        mockServer = creator.getServer();
        mockCommandSender = creator.getCommandSender();
    }

    @After
    public void tearDown() throws Exception {
        creator.tearDown();
    }

    @Test
    public void testEnableDebugMode() {
        // Pull a core instance from the server.
        Plugin plugin = mockServer.getPluginManager().getPlugin("PluginBase");
        MockPlugin myPlugin = (MockPlugin) plugin;

        // Make sure Core is not null
        assertNotNull(plugin);

        // Make sure Core is enabled
        assertTrue(plugin.isEnabled());

        // Make a fake server folder to fool MV into thinking a world folder exists.
        File serverDirectory = new File(creator.getPlugin().getServerFolder(), "world");
        serverDirectory.mkdirs();

        // Initialize a fake command
        Command mockCommand = mock(Command.class);
        when(mockCommand.getName()).thenReturn("pb");

        // Assert debug mode is off
        Assert.assertEquals(0, (int) myPlugin.config().get(BaseConfig.DEBUG_MODE));

        Assert.assertFalse(myPlugin.config().get(BaseConfig.FIRST_RUN));

        // Send the debug command.
        String[] cmdArgs = new String[] { "debug", "3" };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);

        cmdArgs = new String[] { "reload" };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);

        cmdArgs = new String[] { "test" };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);

        cmdArgs = new String[] { "confirm" };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);

        Assert.assertTrue(MockQueuedCommand.TEST);

        cmdArgs = new String[] { "help" };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);
        cmdArgs = new String[] { };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);

        cmdArgs = new String[] { "version", "-p" };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);

        Assert.assertFalse(myPlugin.config().get(BaseConfig.FIRST_RUN));

        Assert.assertEquals(3, (int) myPlugin.config().get(BaseConfig.DEBUG_MODE));
        
        myPlugin.getMessager().good(MockMessages.TEST_MESSAGE, mockCommandSender, "And a test arg");

        Assert.assertEquals(BaseConfig.LOCALE.serialize(BaseConfig.LOCALE.getDefault()).toString(), myPlugin.config().get(BaseConfig.LOCALE).toString());
        
        myPlugin.config().set(BaseConfig.LOCALE, Locale.CANADA);

        Assert.assertEquals(Locale.CANADA, myPlugin.config().get(BaseConfig.LOCALE));
        
        myPlugin.config().save();
        
        Assert.assertEquals(MockConfig.SPECIFIC_TEST.getNewTypeMap(), myPlugin.config().getMap(MockConfig.SPECIFIC_TEST));
        Assert.assertEquals(null, myPlugin.config().get(MockConfig.SPECIFIC_TEST.specific("test1")));
        myPlugin.config().set(MockConfig.SPECIFIC_TEST.specific("test1"), 25);
        Assert.assertEquals(25, (int) myPlugin.config().get(MockConfig.SPECIFIC_TEST.specific("test1")));
        myPlugin.config().save();
        cmdArgs = new String[] { "reload" };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);
        Map<String, Integer> testMap = new HashMap<String, Integer>(1);
        testMap.put("test1", 25);
        Assert.assertEquals(testMap, myPlugin.config().getMap(MockConfig.SPECIFIC_TEST));

        Assert.assertEquals(MockConfig.LIST_TEST.getNewTypeList(), myPlugin.config().getList(MockConfig.LIST_TEST));
        myPlugin.config().set(MockConfig.LIST_TEST, Arrays.asList(25, 41));
        myPlugin.config().save();
        cmdArgs = new String[] { "reload" };
        plugin.onCommand(mockCommandSender, mockCommand, "", cmdArgs);
        List<Integer> checkList = myPlugin.config().getList(MockConfig.LIST_TEST);
        assertTrue(checkList instanceof LinkedList);
        assertTrue(checkList.contains(25) && checkList.contains(41));
    }
}