package hu.asztalosdani.rezplugin.projectView;

import com.intellij.icons.AllIcons;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ProjectViewNodeDecorator;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packageDependencies.ui.PackageDependenciesNode;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.RowIcon;

import javax.swing.*;

public class RezPackageDecorator implements ProjectViewNodeDecorator {

    private Icon packageFileIcon = IconLoader.getIcon("/icons/packageFile.png");
    private Icon packageFolderIcon = IconLoader.getIcon("/icons/packageFolder.png");
    private Icon packageSourcesRootIcon = IconLoader.getIcon("/icons/packageSourcesRoot.png");

    @Override
    public void decorate(ProjectViewNode node, PresentationData data) {
        final VirtualFile file = node.getVirtualFile();
        if (file == null) {
            return;
        }
        if (file.findChild("package.py") != null) {
            Icon originalIcon = data.getIcon(true);

            if (originalIcon instanceof RowIcon) {
                Icon[] allIcons = ((RowIcon) originalIcon).getAllIcons();
                for (Icon icon : allIcons) {
                    if (icon == AllIcons.Modules.SourceRoot) {
                        data.setIcon(packageSourcesRootIcon);
                    }
                }
            } else if (originalIcon == AllIcons.Modules.SourceRoot) {
                data.setIcon(packageSourcesRootIcon);
            } else if (originalIcon == AllIcons.Nodes.Folder) {
                data.setIcon(packageFolderIcon);
            }
        } else if (file.getName().equals("package.py")) {
            data.setIcon(packageFileIcon);
        }
    }

    @Override
    public void decorate(PackageDependenciesNode node, ColoredTreeCellRenderer cellRenderer) {

    }
}
