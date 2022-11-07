package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository
{
    private final EntityManager entityManager;

    @Transactional
    public void save(Item item)
    {
        if (item.getId() == null)
        {
            entityManager.persist(item);
        } else
        {
            entityManager.merge(item);
        }
    }

    @Transactional(readOnly = true)
    public Item findOne(Long id)
    {
        return entityManager.find(Item.class, id);
    }

    @Transactional(readOnly = true)
    public List<Item> findAll()
    {
        return entityManager.createQuery("select i from Item  i", Item.class)
                .getResultList();
    }
}
