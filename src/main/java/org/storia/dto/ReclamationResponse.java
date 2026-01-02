package org.storia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReclamationResponse {

    private Long id;
    private Long userId;
    private String username;
    private String subject;
    private String description;
    private String status;
    private String adminResponse;
    private String respondedByUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime respondedAt;
}
