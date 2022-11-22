package jpabook2.jpashop2.repository;

import jpabook2.jpashop2.domain.item.Book;
import jpabook2.jpashop2.domain.item.Item;
import jpabook2.jpashop2.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class ItemRepositoryTest {
    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;

    @Test
    public void 상품저장() throws Exception {
        //given
        Book book = new Book();
        book.setName("book1");
        book.setPrice(10000);
        //when
        itemService.saveItem(book);

        //then
        System.out.println("book = " + book);
       // System.out.println("itemRepository = " + itemRepository.findOne(itemRepository.save(book)));
       // assertThat(itemRepository.findOne(itemRepository.save(book))).isNotNull();
    }

}