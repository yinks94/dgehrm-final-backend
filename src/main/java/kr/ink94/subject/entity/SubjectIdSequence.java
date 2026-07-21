package kr.ink94.subject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subject_id_sequence")
@Getter
@NoArgsConstructor
public class SubjectIdSequence {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "kind", length = 30)
    private SubjectKind kind;

    @Column(name = "next_value", nullable = false)
    private Long nextValue;

    public SubjectIdSequence(SubjectKind kind, Long nextValue) {
        this.kind = kind;
        this.nextValue = nextValue;
    }

    public Long issue() {
        Long issuedValue = nextValue;
        nextValue++;
        return issuedValue;
    }

    public void ensureNextValueAtLeast(Long nextValue) {
        if (this.nextValue == null || this.nextValue < nextValue) {
            this.nextValue = nextValue;
        }
    }
}
