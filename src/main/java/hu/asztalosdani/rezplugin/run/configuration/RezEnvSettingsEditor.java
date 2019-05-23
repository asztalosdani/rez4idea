package hu.asztalosdani.rezplugin.run.configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.ui.RawCommandLineEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RezEnvSettingsEditor extends SettingsEditor<RezEnvRunConfiguration> {
    private JPanel myPanel;
    private RawCommandLineEditor commandField;
    private RawCommandLineEditor requestedPackagesField;

    @Override
    protected void resetEditorFrom(@NotNull RezEnvRunConfiguration runConfiguration) {
        requestedPackagesField.setText(runConfiguration.getRequestedPackages());
        commandField.setText(runConfiguration.getCommand());
    }

    @Override
    protected void applyEditorTo(@NotNull RezEnvRunConfiguration runConfiguration) throws ConfigurationException {
        runConfiguration.setRequestedPackages(requestedPackagesField.getText());
        runConfiguration.setCommand(commandField.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myPanel;
    }
}
