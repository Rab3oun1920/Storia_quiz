package org.storia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseRequest {

    @NotBlank(message = "La réponse ne peut pas être vide")
    private String response;

    @NotNull(message = "Le statut est requis")
    private String status;  // RESOLU ou FERME
}
