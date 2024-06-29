package com.devoxx.genie.ui.settings.llmsettings;

import com.devoxx.genie.ui.topic.AppTopics;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LLMCostSettingsConfigurable implements Configurable {

    private LLMCostSettingsComponent llmCostSettingsComponent;

    private final MessageBus messageBus;

    public LLMCostSettingsConfigurable(Project project) {
        this.messageBus = project.getMessageBus();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "LLM Costs";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        llmCostSettingsComponent = new LLMCostSettingsComponent();
        llmCostSettingsComponent.addSettingsChangeListener(this::notifySettingsChanged);
        llmCostSettingsComponent.reset(); // This will load the current (including default) values
        return llmCostSettingsComponent.createSettingsPanel();
    }

    private void notifySettingsChanged() {
        messageBus.syncPublisher(AppTopics.LLM_SETTINGS_CHANGED_TOPIC).settingsChanged();
    }

    @Override
    public boolean isModified() {
        return llmCostSettingsComponent.isModified();
    }

    @Override
    public void apply() {
        llmCostSettingsComponent.apply();
        // Notify listeners that settings have changed
        messageBus.syncPublisher(AppTopics.LLM_SETTINGS_CHANGED_TOPIC).settingsChanged();

    }

    @Override
    public void reset() {
        llmCostSettingsComponent.reset();
    }
}
