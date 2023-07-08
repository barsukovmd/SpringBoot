package us.teachmeskills.springdataJpa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.teachmeskills.springdataJpa.models.Item;
import us.teachmeskills.springdataJpa.models.Person;
import us.teachmeskills.springdataJpa.repositories.ItemsRepository;

import java.util.List;

@Service
//Ioc
//DI
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemsRepository itemsRepository;

//    @Autowired
//    public ItemService(ItemsRepository itemsRepository) {
//        this.itemsRepository = itemsRepository;
//    }

    public List<Item> findByItemName(String itemName) {
        return itemsRepository.findByItemName(itemName);
    }

    public List<Item> findByOwner(Person person) {
        return itemsRepository.findByOwner(person);
    }


}
