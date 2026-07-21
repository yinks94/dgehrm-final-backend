package kr.ink94.subject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;
import kr.ink94.subject.entity.SubjectIdSequence;
import kr.ink94.subject.entity.SubjectKind;

public interface SubjectIdSequenceRepository
        extends JpaRepository<SubjectIdSequence, SubjectKind> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SubjectIdSequence> findByKind(SubjectKind kind);
}