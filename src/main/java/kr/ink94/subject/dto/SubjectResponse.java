package kr.ink94.subject.dto;

import kr.ink94.subject.entity.Subject;
import kr.ink94.subject.entity.SubjectKind;

public record SubjectResponse(
        Long id,
        String shortName,
        String fullName,
        SubjectKind kind,
        String neisCode,
        String neisMiddle,
        String neisHigh,
        boolean enable,
        boolean applyCho,
        boolean canTransfer,
        Integer wantCount,
        Long sort
) {

    public static SubjectResponse from(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getShortName(),
                subject.getFullName(),
                subject.getKind(),
                subject.getNeisCode(),
                subject.getNeisMiddle(),
                subject.getNeisHigh(),
                subject.isEnable(),
                subject.isApplyCho(),
                subject.isCanTransfer(),
                subject.getWantCount(),
                subject.getSort()
        );
    }
}