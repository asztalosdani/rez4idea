package hu.asztalosdani.rezplugin.actions;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.jetbrains.python.PythonFileType;
import hu.asztalosdani.rezplugin.ui.NewRezPackageDefinitionDialog;

public class NewDefinitionFileAction extends AnAction {

    private static final String PACKAGE_TEMPLATE = "" +
            "name = \"%s\"\n" +
            "version = \"%s\"\n" +
            "\n" +
            "requires = []\n" +
            "\n" +
            "def commands():\n" +
            "    pass\n";

    @Override
    public void actionPerformed(AnActionEvent e) {
        final IdeView view = e.getRequiredData(LangDataKeys.IDE_VIEW);
        PsiDirectory directory = view.getOrChooseDirectory();
        if (directory == null) {
            return;
        }

        Project project = e.getRequiredData(LangDataKeys.PROJECT);
        NewRezPackageDefinitionDialog dialog = new NewRezPackageDefinitionDialog(project);
        boolean result = dialog.showAndGet();
        if (!result) {
            return;
        }
        String packageName = dialog.getName();
        String packageVersion = dialog.getVersion();

        String packageContent = String.format(PACKAGE_TEMPLATE, packageName, packageVersion);
        PsiFile file = PsiFileFactory.getInstance(project).createFileFromText("package.py", PythonFileType.INSTANCE, packageContent);
        Application application = ApplicationManager.getApplication();
        application.runWriteAction(() -> {
            PsiFile newFile = (PsiFile) directory.add(file);
            FileEditorManager.getInstance(project).openFile(newFile.getVirtualFile(), true);
        });
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final IdeView view = e.getData(LangDataKeys.IDE_VIEW);

        final PsiDirectory directory = view != null ? view.getOrChooseDirectory() : null;
        if (directory == null || project == null || directory.getVirtualFile().findChild("package.py") != null) {
            e.getPresentation().setVisible(false);
        }
    }
}
