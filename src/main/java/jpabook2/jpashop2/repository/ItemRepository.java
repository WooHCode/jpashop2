package jpabook2.jpashop2.repository;

import jpabook2.jpashop2.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public Long save(Item item){
        if (item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
        return item.getId();
    }

    public Item findOne(Long itemId){
        return em.find(Item.class,itemId);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
