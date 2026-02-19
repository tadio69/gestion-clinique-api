package chijouProjects.gestion_clinique_api.service.impl;

import chijouProjects.gestion_clinique_api.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendPrivateMessage(String toUser, String message) {
        // Envoie au canal spécifique de l'utilisateur : /user/{username}/queue/messages
        messagingTemplate.convertAndSendToUser(toUser, "/queue/messages", message);
    }

    @Override
    public void sendNotification(String toUser, String alert) {
        // Envoie une alerte de service (ex: "Nouveau RDV créé")
        messagingTemplate.convertAndSendToUser(toUser, "/queue/notifications", alert);
    }
}
