package notification.impl;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import notification.InboxNotification;

public class InboxNotificationImpl extends InboxNotification {

    private static final long serialVersionUID = -5976192100927192675L;

    private String title;

    private String contentPath;

    private String assignee;

    private String message;

    private String[] notificationActions;

    private String instructions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getNotificationActions() {
        if (notificationActions == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        } else {
            return Arrays.copyOf(notificationActions, notificationActions.length);
        }
    }

    public void setNotificationActions(String... notificationActions) {
        if (notificationActions == null) {
            this.notificationActions = ArrayUtils.EMPTY_STRING_ARRAY;
        } else {
            this.notificationActions = Arrays.copyOf(notificationActions, notificationActions.length);
        }
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}