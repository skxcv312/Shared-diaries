package org.zerock.study.domain.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.study.global.BaseTimeEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "members")
public class MembersEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Builder
    public MembersEntity(String password, String email) {
        this.password = password;
        this.email = email;
    }

}
