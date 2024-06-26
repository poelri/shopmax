package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="order_item")
@Getter
@Setter
@ToString
public class OrderItem extends BaseEntity{
    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orderPrice;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //OrdetItem이 Order를 참조한다

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count); // item 객체 안의 재고 변경

        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice * count; // 총가격
    }

    // 재고를 원래대로
    public void cancel(){
        // 이전에 주문을 한 만큼(count) 다시 item 의 stock을 더해준다.
        this.getItem().addStock(count); // private int count; 이 count 당~!
    }

}
