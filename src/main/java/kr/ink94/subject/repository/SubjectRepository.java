package kr.ink94.subject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import kr.ink94.subject.entity.Subject;
import kr.ink94.subject.entity.SubjectKind;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findByShortName(String shortName);

    Optional<Subject> findByFullName(String fullName);

    List<Subject> findByKind(SubjectKind kind);

    List<Subject> findByEnable(boolean enable);

    @Query("SELECT MAX(s.id) FROM Subject s WHERE s.kind = :kind")
    Optional<Long> findMaxIdByKind(@Param("kind")SubjectKind kind);
}
