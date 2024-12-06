package com.devoxx.genie.ui.component;

import com.devoxx.genie.ui.DevoxxGenieToolWindowContent;
import com.devoxx.genie.ui.panel.ActionButtonsPanel;
import com.devoxx.genie.ui.panel.PromptContextFileListPanel;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class SubmitPanel extends JBPanel<SubmitPanel> {

    public static final int MIN_INPUT_HEIGHT = 100;
    private final Project project;

    private final DevoxxGenieToolWindowContent toolWindowContent;
    private PromptContextFileListPanel promptContextFileListPanel;
    @Getter
    private final PromptInputArea promptInputArea;

    @Getter
    private ActionButtonsPanel actionButtonsPanel;


    public SubmitPanel(DevoxxGenieToolWindowContent devoxxGenieToolWindowContent, Project project, ResourceBundle resourceBundle)

    {
        super(new BorderLayout());
        this.toolWindowContent = devoxxGenieToolWindowContent;
        this.project = project;

        promptContextFileListPanel = new PromptContextFileListPanel(project);
        promptInputArea = new PromptInputArea(resourceBundle, project);

        JPanel submitPanel = new JPanel(new BorderLayout());
        submitPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, MIN_INPUT_HEIGHT));
        submitPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, MIN_INPUT_HEIGHT));
        submitPanel.add(promptContextFileListPanel, BorderLayout.NORTH);
        submitPanel.add(new JBScrollPane(promptInputArea), BorderLayout.CENTER);
        submitPanel.add(createActionButtonsPanel(), BorderLayout.SOUTH);

        add(submitPanel);
    }


    /**
     * The bottom action buttons panel (Submit, Search buttons and Add Files)
     *
     * @return the action buttons panel
     */
    @Contract(" -> new")
    private @NotNull JPanel createActionButtonsPanel() {
        actionButtonsPanel = new ActionButtonsPanel(project,
                promptInputArea,
                toolWindowContent.getPromptOutputPanel(),
                toolWindowContent.getLlmProviderPanel().getModelProviderComboBox(),
                toolWindowContent.getLlmProviderPanel().getModelNameComboBox(),
                toolWindowContent);
        return actionButtonsPanel;
    }
}
