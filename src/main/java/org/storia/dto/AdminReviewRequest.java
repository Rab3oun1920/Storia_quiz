package org.storia.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReviewRequest {

    @NotNull(message = "Le statut est requis")
    private String status;  // APPROUVE ou REJETE

    private String comment;  // Commentaire optionnel de l'admin
}
