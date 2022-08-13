package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;


// Spring Data JPA 는 인테페이스 선언만으로도 자동으로 스프링 Bean 으로 등록
// : 스프링이 내부적으로 인터페이스 타입에 맞는 객체를 생성해서 빈으로 등록한다.
public interface MemoRepository extends JpaRepository<Memo, Long> {
    /**
     * insert : save
     * select : findById
     * update : save
     * delete : deleteById
     */


    /** JPQL(Java Persistence Query Language) 객체 지향 쿼리 기능
     * 다양한 검색 조건: 특정 범위, like 처리, 여러 검색 조건 처리
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
     *
     * 쿼리 메서드 : 메서드의 이름 자체가 쿼리의 구문으로 처리되는 기능
     * @Query : SQL 과 유사하게 엔티티 클래스의 정보를 이용해서 쿼리를 작성하는 기능
     * Querydsl : 동적 쿼리 처리 기능
     */

    // 쿼리메서드 : 검색 등 간단한 쿼리

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // 쿼리메서드와 Pageable 의 결합
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    // deleteBy 삭제처리 : 파리미터 번호 보다 작은 데이터 삭제
    void deleteMemoByMnoLessThan(Long num);


    /** @Query 어노테이션 : 조인이나 복잡한 조건을 처리해야 하는 경우
     * 필요한 데이터만 선별적으로 추출하는 기능이 가능
     * 데이터베이스에 맞는 순수한 sql 사용이 가능
     * DML 등을 처리하는 기능(@Modifying 과 함께 사용)
     */
    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    /** Query 의 파라미터 바인딩
     * '?1, ?2' : 파라미터 순서을 이용하는 방식
     * ':xxx' : 파라미터 이름을 활용하는 방식
     * '#{}' : 자바 빈 스타일을 이용하는 방식
     */
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
    int updateMemoText(@Param("param") Memo memo);


    // Object[] 리턴
    @Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno",
        countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);


    // Native SQL 처리
    @Query(value = "select * from tbl_memo where mno > 95", nativeQuery = true)
    List<Object[]> getNativeResult();
}
