package kr.ink94.subject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="subjects")
@Getter
@Setter
@NoArgsConstructor
public class Subject {

    @Id
    @Column(name="subject_id")
    private Long id;

    @Column(name="short_name", nullable = false, length = 20)
    private String shortName;

    @Column(name="full_name", nullable = false, length = 20)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private SubjectKind kind;

    @Column(name="neis_code", nullable = true, length = 20)
    private String neisCode;

    @Column(name="neis_middle", nullable = true, length = 20)
    private String neisMiddle;

    @Column(name="neis_high", nullable = true, length = 20)
    private String neisHigh;

    @Column(name="enabled", nullable = false)
    private boolean enable = true;

    @Column(name="apply_cho", nullable = false)
    private boolean applyCho = false;

    @Column(name="can_transfer", nullable = false)
    private boolean canTransfer = true;

    @Column(name="want_count", nullable = false)
    private Integer wantCount = 4;

    private Long sort;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="modified_at", nullable = false)
    private LocalDateTime modifiedAt;


    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        modifiedAt = LocalDateTime.now();
    }
}
