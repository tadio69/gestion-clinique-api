package chijouProjects.gestion_clinique_api.dto;

public record AuthResponseDTO(
        String token,
        String username,
        String role
) {}
