package org.storia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropositionThemeRequest {

    @NotBlank(message = "Le titre du thème ne peut pas être vide")
    @Size(max = 100, message = "Le titre ne peut pas dépasser 100 caractères")
    private String themeTitle;

    @NotBlank(message = "La description ne peut pas être vide")
    private String themeDescription;

    @Size(max = 50, message = "L'icône ne peut pas dépasser 50 caractères")
    private String themeIcon;
}
