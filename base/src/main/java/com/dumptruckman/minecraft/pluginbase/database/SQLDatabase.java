package com.dumptruckman.minecraft.pluginbase.database;

import com.dumptruckman.minecraft.pluginbase.plugin.PluginBase;

import java.net.MalformedURLException;
import java.sql.ResultSet;

public interface SQLDatabase {

    boolean isConnected();

    boolean connect(PluginBase plugin);

    void disconnect();

    ResultSet query(String string);

    boolean createTable(String query);

    boolean checkTable(String name) throws MalformedURLException, InstantiationException, IllegalAccessException;

    boolean wipeTable(String table) throws MalformedURLException, InstantiationException, IllegalAccessException;
}
