package com.shopmax.repository;

import com.shopmax.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 현재 로그인한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회

    @Query("select o from Order o where o.member.email = :email order by o.orderDate desc")
    // 오더에서 모든((o) * 라고쓰면 XX) 컬럼 가져와라 ㅋ 쿼리문 작성~! @Query로  ㄱㄱ
    // 멤버에서 로그인한 사용자의 이메일믄 가져와서 정렬할꼬야
    List<Order> findOrders(@Param("email") String email, Pageable pageable);
    // 주문내역을 가져오셍. 메소드 이름은 마음대로 지어도 됩니당 .
    // pageable 페이징 처리 할려고 쓰는거임


    // 현재 로그인한 회원의 주문 개수가 몇개인지 조회(total)
    @Query("select count(o) from Order o where o.member.email = :email")
    Long countOrder(@Param("email") String email);
    // 내가 카운터오더를 실행하면 email을 매개변수로 받아들이고, 이메일에 해당하는 주문의 수를 반환하는 것
}
