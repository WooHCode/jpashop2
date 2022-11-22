package jpabook2.jpashop2.service;

import jpabook2.jpashop2.domain.Address;
import jpabook2.jpashop2.domain.Member;
import jpabook2.jpashop2.domain.Order;
import jpabook2.jpashop2.domain.OrderStatus;
import jpabook2.jpashop2.domain.item.Book;
import jpabook2.jpashop2.domain.item.Item;
import jpabook2.jpashop2.exception.NotEnoughStockException;
import jpabook2.jpashop2.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("첫번째책", 10000, 100);
        int orderCount = 2;
        //when
        Long orderId = orderService.Order(member.getId(), item.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertEquals(order.getStatus(), OrderStatus.ORDER,"상품 주문시 상태는 ORDER");
        assertEquals(order.getOrderItems().size(),1,"주문상품 종류 수가 같아야한다.");
        assertEquals(order.getTotalPrice(),20000,"주문 가격은 주문수량 * 가격");
        assertEquals(item.getStockQuantity(),98,"주문수량만큼 총량이 줄어야한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("firstBook", 10000, 10);
        int orderCount = 11;

        //when

        //then
        assertThrows(NotEnoughStockException.class, ()->{
            orderService.Order(member.getId(), item.getId(), orderCount);
        });
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item book1 = createBook("book1", 10000, 10);
        int orderCount = 5;
        Long order = orderService.Order(member.getId(), book1.getId(), orderCount);

        //when
        orderService.cancelOrder(order);
        //then
        Order one = orderRepository.findOne(order);

        assertEquals(one.getStatus(),OrderStatus.CANCEL,"주문 취소 시 상태는 CANCEL");
        assertEquals(book1.getStockQuantity(),10,"주문 취소 시 상태는 그만큼 복구되어야함.");


    }

    private Item createBook(String bookName, int bookPrice, int stockQuantity) {
        Book book = new Book();
        book.setName(bookName);
        book.setPrice(bookPrice);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("user1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);
        return member;
    }

}