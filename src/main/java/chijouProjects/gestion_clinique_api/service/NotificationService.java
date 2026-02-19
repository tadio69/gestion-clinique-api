package chijouProjects.gestion_clinique_api.service;

public interface NotificationService {
    // Pour le chat privé entre utilisateurs
    void sendPrivateMessage(String toUser, String message);

    // Pour les alertes automatiques (Nouveau RDV, etc.)
    void sendNotification(String toUser, String alert);
}
