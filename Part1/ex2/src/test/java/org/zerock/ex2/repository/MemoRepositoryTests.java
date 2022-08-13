package org.zerock.ex2.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println("=====================================");
        System.out.println(memoRepository.getClass().getName());
    }

//    @Test
//    public void for_loop(){
//        for(int i=0; i<10; i++) System.out.println(i);
//    }
//
//    @Test
//    public void intStream_range(){
//        IntStream.rangeClosed(1, 11).forEach(System.out::println);
//    }

    // 생성
    @Test
    public void testInsertDummies(){

        IntStream.rangeClosed(1, 100).forEach(i->{
            Memo memo = Memo.builder().memoText("Sample.... " + i).build();
            memoRepository.save(memo);
        });
    }

    // 조회 1
    @Test
    public void testSelect(){
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("Select 1 ===============================");
        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    // 조회 2
    @Transactional
    @Test
    public void testSelect2(){
        Long mno = 100L;
        Memo memo = memoRepository.getReferenceById(mno);
        System.out.println("Select 2 ===============================");
        System.out.println(memo); // 객체를 사용하는 순간에 SQL 이 동작
    }

    // 수정 : (.save) 내부적으로 해당 엔티티의 @Id 값이 일치하는지를 확인해서 insert 혹은 update 작업 처리
    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
    }

    // 삭제
    @Test
    public void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    // 페이징 처리
    @Test
    public void testPageDefault(){

        // 1페이지(0부터시작) 10개 가져오기
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println("Page ===============================");
        System.out.println("Total Pages: " + result.getTotalPages());           // 총 몇 페이지
        System.out.println("Total Count: " + result.getTotalElements());        // 천체 개수
        System.out.println("Page Number: " + result.getNumber());               // 현재 페이지 번호
        System.out.println("Page   Size: " + result.getSize());                 // 페이지당 테이터 개수
        System.out.println("hasNextPage: " + result.hasNext());                 // 다음 페이지 존재여부
        System.out.println("first  Page: " + result.isFirst());                 // 시작 페이지(0) 여부

        System.out.println("Entity =============================");
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    // 정렬 조건 추가하기
    @Test
    public void testSort(){
        // 내림차순(역) 정렬
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); // and 를 이용한 연결

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    /** JPQL(Java Persistence Query Language) 객체 지향 쿼리 기능
     * 다양한 검색 조건: 특정 범위, like 처리, 여러 검색 조건 처리
     *
     * 쿼리 메서드 : 메서드의 이름 자체가 쿼리의 구문으로 처리되는 기능
     * @Query : SQL 과 유사하게 엔티티 클래스의 정보를 이용해서 쿼리를 작성하는 기능
     * Querydsl : 동적 쿼리 처리 기능
     */

    // mno 값이 70부터 80 사이의 객체들 구해서 역순으로 정렬
    @Test
    public void testQueryMethods(){

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for(Memo memo : list){
            System.out.println(memo);
        }
    }

    // 쿼리메서드와 Pageable 의 결합
    @Test
    public void testQueryMethodWithPageable(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    // deleteBy 삭제처리 : 파리미터 번호 보다 작은 데이터 삭제
    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods(){

        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    // @Query 어노테이션
    @Test
    public void testQueryAnnotation(){
        List<Object[]> list = memoRepository.getNativeResult();
        for(Object[] objects : list){
            for(int i=0; i<objects.length; i++){
                System.out.print(objects[i] + " ");
            }
            System.out.println();
        }
    }

}
