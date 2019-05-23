package hu.asztalosdani.rezplugin.toolwindow;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.*;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class PackagesToolWindow {
    private JPanel toolWindowPanel;
    private Tree packagesTree;
    private JButton button1;

    public PackagesToolWindow(ToolWindow toolWindow) {

        packagesTree.setRootVisible(false);

        findPackages();
    }

    public JPanel getContent() {
        return toolWindowPanel;
    }

    private void findPackages() {
        ArrayList<String> command = new ArrayList<>();
        command.add("rez-search");
        command.add("--no-newlines");
        command.add("--format");
        command.add("{name}|{version}");
        command.add("-t");
        command.add("package");
        command.add("*");

        GeneralCommandLine generalCommandLine = new GeneralCommandLine(command);
        generalCommandLine.setCharset(Charset.forName("UTF-8"));

        HashMap<String, ArrayList<String>> packages = new HashMap<>();

        try {
            OSProcessHandler processHandler = new OSProcessHandler(generalCommandLine);
            processHandler.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
                    if (outputType == ProcessOutputTypes.STDOUT) {
                        String line = event.getText().trim();
                        String[] parts = line.split("\\|");
                        String packageName = parts[0];
                        String packageVersion = parts[1];

                        if (!packages.containsKey(packageName)) {
                            packages.put(packageName, new ArrayList<>());
                        }
                        packages.get(packageName).add(packageVersion);
                    }
                }
            });
            processHandler.startNotify();
            processHandler.waitFor();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        DefaultTreeModel model = (DefaultTreeModel) packagesTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        packages.forEach((packageName, versions) -> {

            DefaultMutableTreeNode packageNode = new DefaultMutableTreeNode(packageName);
            root.add(packageNode);
            for (String version : versions) {
                packageNode.add(new DefaultMutableTreeNode(version));
            }
        });

        model.reload(root);
    }

}
