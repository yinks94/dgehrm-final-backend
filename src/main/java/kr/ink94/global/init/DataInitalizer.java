package kr.ink94.global.init;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.ink94.member.entity.Member;
import kr.ink94.member.entity.Role;
import kr.ink94.member.repository.MemberRepository;
import kr.ink94.subject.entity.Subject;
import kr.ink94.subject.entity.SubjectIdSequence;
import kr.ink94.subject.entity.SubjectKind;
import kr.ink94.subject.repository.SubjectIdSequenceRepository;
import kr.ink94.subject.repository.SubjectRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitalizer {

  private final String DEFAULT_PASSWORD = "1234";

  @Bean
  CommandLineRunner initializeSubjects(
      SubjectRepository subjectRepository,
      SubjectIdSequenceRepository subjectIdSequenceRepository) {

    return args -> {
      List<SubjectSeed> seeds = List.of(
          new SubjectSeed(1001L, "국어", "국어", SubjectKind.교과, true, true, true, 4, 1L),
          new SubjectSeed(1002L, "수학", "수학", SubjectKind.교과, true, true, true, 4, 2L),
          new SubjectSeed(1003L, "영어", "영어", SubjectKind.교과, true, true, true, 4, 3L),
          new SubjectSeed(1004L, "사회", "사회", SubjectKind.교과, true, true, true, 4, 4L),
          new SubjectSeed(1005L, "과학", "과학", SubjectKind.교과, true, true, true, 4, 5L),
          new SubjectSeed(2001L, "전문", "전문교과", SubjectKind.전문교과, true, false, true, 4, 1L),
          new SubjectSeed(3001L, "비교과", "비교과", SubjectKind.비교과, true, false, false, 4, 1L),
          new SubjectSeed(4001L, "특수", "특수", SubjectKind.특수, true, false, false, 4, 1L));

      for (SubjectSeed seed : seeds) {
        if (!subjectRepository.existsById(seed.id())) {
          subjectRepository.save(seed.toEntity());
        }
      }

      initializeSubjectIdSequence(subjectIdSequenceRepository, SubjectKind.교과, 1006L);
      initializeSubjectIdSequence(subjectIdSequenceRepository, SubjectKind.전문교과, 2002L);
      initializeSubjectIdSequence(subjectIdSequenceRepository, SubjectKind.비교과, 3002L);
      initializeSubjectIdSequence(subjectIdSequenceRepository, SubjectKind.특수, 4002L);
    };
  }

  @Bean
  CommandLineRunner initializeMembers(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {

    return args -> {
      if (!memberRepository.existsByMemberId("metro_master")) {
        memberRepository.save(
            new Member("metro_master",
                "시관리자",
                passwordEncoder.encode(DEFAULT_PASSWORD),
                Role.ROLE_METRO_MASTER));
      }

      if (!memberRepository.existsByMemberId("region_master")) {
        memberRepository.save(
            new Member("region_master",
                "지역관리자",
                passwordEncoder.encode(DEFAULT_PASSWORD),
                Role.ROLE_REGION_MASTER));
      }

      if (!memberRepository.existsByMemberId("metro_app")) {
        memberRepository.save(
            new Member("metro_app",
                "시배정자",
                passwordEncoder.encode(DEFAULT_PASSWORD),
                Role.ROLE_METRO_APP));
      }

      if (!memberRepository.existsByMemberId("region_app")) {
        memberRepository.save(
            new Member("region_app",
                "지역배정자",
                passwordEncoder.encode(DEFAULT_PASSWORD),
                Role.ROLE_REGION_APP));
      }
    };
  }

  private void initializeSubjectIdSequence(
      SubjectIdSequenceRepository subjectIdSequenceRepository,
      SubjectKind kind,
      Long nextValue) {

    SubjectIdSequence sequence = subjectIdSequenceRepository.findById(kind)
        .orElseGet(() -> new SubjectIdSequence(kind, nextValue));

    sequence.ensureNextValueAtLeast(nextValue);
    subjectIdSequenceRepository.save(sequence);
  }

  private record SubjectSeed(
      Long id,
      String shortName,
      String fullName,
      SubjectKind kind,
      boolean enable,
      boolean applyCho,
      boolean canTransfer,
      Integer wantCount,
      Long sort) {

    Subject toEntity() {
      Subject subject = new Subject();
      subject.setId(id);
      subject.setShortName(shortName);
      subject.setFullName(fullName);
      subject.setKind(kind);
      subject.setEnable(enable);
      subject.setApplyCho(applyCho);
      subject.setCanTransfer(canTransfer);
      subject.setWantCount(wantCount);
      subject.setSort(sort);
      return subject;
    }
  }
}
