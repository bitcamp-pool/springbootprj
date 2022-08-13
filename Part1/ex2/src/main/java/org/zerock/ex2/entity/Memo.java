package org.zerock.ex2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_memo")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 키 생성 전략(AUTO:Hibernate, IDENTITY:사용 DB 방식
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
