package hu.asztalosdani.rezplugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class NewRezPackageDefinitionDialog extends DialogWrapper {
    private JTextField packageName;
    private JTextField packageVersion;
    private JPanel panel;

    public NewRezPackageDefinitionDialog(Project project) {
        super(project);
        init();
        setTitle("Create New Package Definition File");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel;
    }

    public String getName() {
        return packageName.getText();
    }

    public String getVersion() {
        return packageVersion.getText();
    }
}
