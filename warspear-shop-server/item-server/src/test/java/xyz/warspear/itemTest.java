package xyz.warspear;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.warspear.entity.dto.ItemDetailedDto;
import xyz.warspear.entity.po.Item;
import xyz.warspear.repository.ItemRepository;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class itemTest {
    @Autowired
    ItemRepository itemRepository;
    @Test
    @Transactional
    public void ItemetailedDtoTest(){
        Item item = itemRepository.getOne(1);
        ItemDetailedDto itemDetailedDto = new ItemDetailedDto(item);
        System.out.println(itemDetailedDto.getUsername());
    }
}
