package notification;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import aQute.bnd.annotation.ProviderType;


/**
 * OSGi Service that is used to send AEM Notifications notifications.
 */
@ProviderType
public interface InboxNotificationSender {

    /**
     * Sends a AEM inbox Notification.
     *
     * @param resourceResolver the resource resolver used to send the notification.
     * @param inboxNotification the notification to send.
     * @throws TaskManagerException
     */
	public void sendInboxNotification(ResourceResolver resourceResolver,
            InboxNotification inboxNotification) throws Exception;

    /**
     * Sends multiple AEM inbox notifications.
     * @param resourceResolver the resource resolver used to send the notification.
     * @param inboxNotifications the notifications to send.
     * @throws TaskManagerException
     */
    public void sendInboxNotifications(ResourceResolver resourceResolver,
            List<InboxNotification> inboxNotifications)
            throws Exception;

    /**
     * Builds an InboxNotifcation object that can be populate prior to sending.
     * @return a blank InboxNotification object.
     */
    public InboxNotification buildInboxNotification();
}