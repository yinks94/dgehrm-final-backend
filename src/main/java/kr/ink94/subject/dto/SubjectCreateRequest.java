package kr.ink94.subject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.ink94.subject.entity.SubjectKind;

public record SubjectCreateRequest(

        @NotBlank
        @Size(max = 20)
        String shortName,

        @NotBlank
        @Size(max = 20)
        String fullName,

        @NotNull
        SubjectKind kind,

        @Size(max = 20)
        String neisCode,

        @Size(max = 20)
        String neisMiddle,

        @Size(max = 20)
        String neisHigh,

        Boolean enable,
        Boolean applyCho,
        Boolean canTransfer,
        Integer wantCount,
        Long sort
) {
}