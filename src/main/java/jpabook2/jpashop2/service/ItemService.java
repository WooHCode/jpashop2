package jpabook2.jpashop2.service;

import jpabook2.jpashop2.domain.item.Book;
import jpabook2.jpashop2.domain.item.Item;
import jpabook2.jpashop2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    /**
     * 상품 저장
     */
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    /**
     * 상품 업데이트
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item item = itemRepository.findOne(itemId);
        item.change(name,price,stockQuantity);
    }

    /**
     * 상품 한건 조회(ID)
     */
    public Item findOne(Long itemId){
       return itemRepository.findOne(itemId);
    }

    /**
     * 상품 전체 조회
     */
    public List<Item> findAll(){
        return itemRepository.findAll();
    }
}
