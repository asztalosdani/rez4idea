import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.File;

public class HelloAction extends AnAction {
    public HelloAction() {
        super("Hello");
    }

    public void actionPerformed(AnActionEvent event) {
        PluginId pluginId = PluginManagerCore.getPluginByClassName(getClass().getName());

        IdeaPluginDescriptor descriptor = PluginManager.getPlugin(pluginId);

        File jarPath = new File(descriptor.getPath(), "lib/extraJars/additional.jar");
        Project project = event.getProject();
        Messages.showMessageDialog(project, "Hello world!\n" + jarPath.getAbsolutePath(), "Greeting", Messages.getInformationIcon());
    }
}