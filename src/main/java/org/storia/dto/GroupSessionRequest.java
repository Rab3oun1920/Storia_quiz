package org.storia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSessionRequest {

    @NotBlank
    private String sessionName;

    @NotNull
    private Long themeId;

    private List<GroupRequest> groups;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupRequest {

        @NotBlank
        private String groupName;

        @NotNull
        private Integer order;
    }
}
