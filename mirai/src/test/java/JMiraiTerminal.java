import me.plugin.JPlugin;
import net.mamoe.mirai.console.MiraiConsole;
import net.mamoe.mirai.console.plugin.PluginManager;
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal;
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;

import java.io.File;

public class JMiraiTerminal {
    public static void main(String[] args) {
        System.setProperty("user.dir", new File("mirai-sandbox").getAbsolutePath());
        MiraiConsoleTerminalLoader.INSTANCE.startAsDaemon(new MiraiConsoleImplementationTerminal());
        PluginManager.INSTANCE.loadPlugin(JPlugin.INSTANCE);
        PluginManager.INSTANCE.enablePlugin(JPlugin.INSTANCE);
        MiraiConsole.INSTANCE.getJob().start();
    }
}
