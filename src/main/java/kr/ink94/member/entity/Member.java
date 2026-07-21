package kr.ink94.member.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
public class Member {

  public Member(String memberId, String username, String password, Role role) {
    this.memberId = memberId;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="member_id", nullable = false, unique = true, length = 20)
  private String memberId;

  @Column(name="username", nullable = false, length = 20)
  private String username;

  @Column(name="password", nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name="role", nullable = false)
  private Role role;

  @Column(name="enabled", nullable = false)
  private boolean enabled = true;

  @Column(name="created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name="modified_at", nullable = false)
  private LocalDateTime modifiedAt;


  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    modifiedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    modifiedAt = LocalDateTime.now();
  }
}
