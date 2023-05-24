package us.teachmeskills.springdataJpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.teachmeskills.springdataJpa.models.Item;
import us.teachmeskills.springdataJpa.repositories.ItemsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findByItemName(String itemName) {
        return itemsRepository.findByItemName(itemName);
    }
}
