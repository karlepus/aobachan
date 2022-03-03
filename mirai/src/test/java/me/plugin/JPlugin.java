package me.plugin;

import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import org.jetbrains.annotations.NotNull;

public final class JPlugin extends JavaPlugin {
    public static JvmPlugin INSTANCE = new JPlugin();

    private JPlugin() {
        super(new JvmPluginDescriptionBuilder("me.plugin", "1.0.0").build());
    }

    @Override
    public void onEnable() {
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        super.onLoad($this$onLoad);
    }
}
