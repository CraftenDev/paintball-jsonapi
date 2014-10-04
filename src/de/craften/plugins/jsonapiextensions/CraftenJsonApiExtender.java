package de.craften.plugins.jsonapiextensions;

import com.alecgorge.minecraft.jsonapi.JSONAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftenJsonApiExtender extends JavaPlugin {
    public static CraftenJsonApiExtender instance;

    @Override
    public void onEnable() {
        instance = this;

        Plugin checkplugin = this.getServer().getPluginManager()
                .getPlugin("JSONAPI");
        if (checkplugin != null) {
            // get the JSONAPI instance
            JSONAPI jsonapi = (JSONAPI) checkplugin;

            jsonapi.registerAPICallHandler(new PlotmeExtensions(getServer()));
            jsonapi.registerAPICallHandler(new PaintballExtensions());
        }
    }

    @Override
    public void onDisable() {

    }
}
