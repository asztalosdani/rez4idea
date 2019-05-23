package hu.asztalosdani.rezplugin.run.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RezEnvConfigurationType implements ConfigurationType {
    @NotNull
    @Override
    public String getDisplayName() {
        return "rez-env";
    }

    @Nls
    @Override
    public String getConfigurationTypeDescription() {
        return "Resolve an environment";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.General.Information;
    }

    @NotNull
    @Override
    public String getId() {
        return "REZ_ENV_RUN_CONFIGURATION";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new RezEnvConfigurationFactory(this)};
    }
}
