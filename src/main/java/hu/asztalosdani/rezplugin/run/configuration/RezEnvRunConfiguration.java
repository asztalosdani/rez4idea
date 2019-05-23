package hu.asztalosdani.rezplugin.run.configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RezEnvRunConfiguration extends RunConfigurationBase {
    public static final String REQUESTED_PACKAGES = "REQUESTED_PACKAGES";
    public static final String COMMAND = "COMMAND";

    private String requestedPackages = "";
    private String command = "";

    protected RezEnvRunConfiguration(@NotNull Project project, @Nullable ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new RezEnvSettingsEditor();
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new CommandLineState(environment) {
            @NotNull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {

                // TODO set working directory
                GeneralCommandLine cmd = new GeneralCommandLine();
                cmd.setExePath("rez-env");

                cmd.addParameters(RezEnvRunConfiguration.this.requestedPackages.split(" "));
                cmd.addParameter("--");
                cmd.addParameters(RezEnvRunConfiguration.this.command.split(" "));

                OSProcessHandler processHandler = new OSProcessHandler(cmd);
                ProcessTerminatedListener.attach(processHandler, getEnvironment().getProject());

                return processHandler;
            }
        };
    }

    @Override
    public void readExternal(@NotNull Element element) {
        super.readExternal(element);
        requestedPackages = JDOMExternalizerUtil.readField(element, REQUESTED_PACKAGES);
        command = JDOMExternalizerUtil.readField(element, COMMAND);
    }

    @Override
    public void writeExternal(@NotNull Element element) throws WriteExternalException {
        super.writeExternal(element);
        JDOMExternalizerUtil.writeField(element, REQUESTED_PACKAGES, requestedPackages);
        JDOMExternalizerUtil.writeField(element, COMMAND, command);
    }

    String getRequestedPackages() {
        return requestedPackages;
    }

    void setRequestedPackages(String requestedPackages) {
        this.requestedPackages = requestedPackages;
    }

    String getCommand() {
        return command;
    }

    void setCommand(String command) {
        this.command = command;
    }
}
