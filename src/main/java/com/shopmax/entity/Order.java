package com.shopmax.entity;

import com.shopmax.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@ToString
public class Order {
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY
        , orphanRemoval = true) // 연관관계 주인을 설정(order는 주인이 아니다)
    private List<OrderItem> orderItems = new ArrayList<>();


    // ★ 양방향 참조시 save를 진행할때는 서로가 참조하는 객체를 꼭 넣어줘야 한다.
    public void addOrderItem(OrderItem orderItem) {
        // 양방향 참조 관계 일때는 자식 엔티티
        orderItems.add(orderItem);
        orderItem.setOrder(this); // ★ 양방향 참조관계일때는 orderItem 객체에도 order 객체를 세팅
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList){
            Order order = new Order();
            order.setMember(member);

            for (OrderItem orderItem : orderItemList){
                order.addOrderItem(orderItem);
            }

            order.setOrderStatus(OrderStatus.ORDER);
            order.setOrderDate(LocalDateTime.now());

            return order;

    }

    // 총 주문 금액
    public int getTotalPrice(){
        int totalPrice = 0 ;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        // 재고를 원래대로 돌려놓는다.
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }


}
