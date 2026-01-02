package org.storia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReclamationRequest {

    @NotBlank(message = "Le sujet ne peut pas être vide")
    @Size(max = 200, message = "Le sujet ne peut pas dépasser 200 caractères")
    private String subject;

    @NotBlank(message = "La description ne peut pas être vide")
    private String description;
}
