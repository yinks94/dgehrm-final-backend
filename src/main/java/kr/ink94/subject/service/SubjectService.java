package kr.ink94.subject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ink94.subject.dto.SubjectCreateRequest;
import kr.ink94.subject.dto.SubjectResponse;
import kr.ink94.subject.entity.Subject;
import kr.ink94.subject.entity.SubjectIdSequence;
import kr.ink94.subject.entity.SubjectKind;
import kr.ink94.subject.repository.SubjectIdSequenceRepository;
import kr.ink94.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectIdSequenceRepository subjectIdSequenceRepository;

    @Transactional
    public SubjectResponse create(SubjectCreateRequest request) {
       SubjectIdSequence sequence = subjectIdSequenceRepository
            .findByKind(request.kind())
            .orElseThrow(() -> new IllegalStateException("과목 ID 생성을 위한 kind 정보가 없습니다."));

        Long subjectId = sequence.issue();

        validateDuplicate(request);

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setShortName(request.shortName());
        subject.setFullName(request.fullName());
        subject.setKind(request.kind());
        subject.setNeisCode(request.neisCode());
        subject.setNeisMiddle(request.neisMiddle());
        subject.setNeisHigh(request.neisHigh());
        subject.setEnable(request.enable() == null ? true : request.enable());
        subject.setApplyCho(request.applyCho() == null ? false : request.applyCho());
        subject.setCanTransfer(request.canTransfer() == null ? true : request.canTransfer());
        subject.setWantCount(request.wantCount() == null ? 4 : request.wantCount());
        subject.setSort(request.sort());
        Subject savedSubject = subjectRepository.save(subject);
        return SubjectResponse.from(savedSubject);
    }


    @SuppressWarnings("unused")
    private Long nextSubjectId(SubjectKind kind){
        long startId = switch (kind) {
            case 교과 -> 1001L;
            case 전문교과 -> 2001L;
            case 비교과 -> 3001L;
            case 특수 -> 4000L;
        };

        return subjectRepository.findMaxIdByKind(kind)
                    .map( maxId -> maxId + 1)
                    .orElse(startId);
    }


    private void validateDuplicate(SubjectCreateRequest request) {
        if (subjectRepository.findByShortName(request.shortName()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 과목 약칭입니다.");
        }

        if (subjectRepository.findByFullName(request.fullName()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 과목명입니다.");
        }
    }
}
