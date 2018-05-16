package notification;

import aQute.bnd.annotation.ConsumerType;

import java.io.Serializable;

/**
 * Represents a Inbox Notification.
 *
 * This is a sub-set of attributes of the generic AEM Task object.
 */
@ConsumerType
public abstract class InboxNotification implements Serializable {

    /**
     * @return the notification title.
     */
    public abstract String getTitle();

    /**
     * Sets the notification title.
     * @param title the title
     */
    public abstract void setTitle(String title);

    /**
     * @return the notifications associated content path.
     */
    public abstract String getContentPath();

    /**
     * Sets the content path.
     * @param contentPath the content path
     */
    public abstract void setContentPath(String contentPath);

    /**
     * @return the principal name of the notification recipient
     */
    public abstract String getAssignee();

    /**
     * Sets the assignee.
     * @param assignee the principal name of the notification recipient
     */
    public abstract void setAssignee(String assignee);

    /**
     * @return the notification message.
     */
    public abstract String getMessage();

    /**
     * Sets the message.
     * @param message the message.
     */
    public abstract void setMessage(String message);

    /**
     * Gets the notification's actions.
     * @return the notification's actions.
     */
    public abstract String[] getNotificationActions();

    /**
     * Sets the notification's actions.
     * @param notificationActions the notification's actions.
     */
    public abstract void setNotificationActions(String... notificationActions);

    /**
     * @return the notification's instructions.
     */
    public abstract String getInstructions();

    /**
     * Sets the notification's instructions.
     * @param instructions the instructions.
     */
    public abstract void setInstructions(String instructions);
}