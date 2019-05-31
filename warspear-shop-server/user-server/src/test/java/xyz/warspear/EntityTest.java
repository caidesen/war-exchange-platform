package xyz.warspear;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.warspear.entity.po.*;
import xyz.warspear.repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EntityTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PicRepository picRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * 测试表关系，像表插入测试数据
     */
    @Test
    public void insertTest() {
        //权限
        Permission permission1 = new Permission();
        permission1.setPId(1);
        permission1.setUrl("item/itemList");
        permission1.setDescription("获取商品列表");
        Permission permission2 = new Permission();
        permission2.setPId(2);
        permission2.setUrl("item/itemInfo");
        permission2.setDescription("获取商品详情");

        //角色
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setDescription("游客");
        role1.setRoleName("visitor");
        ArrayList<Permission> permissions1 = new ArrayList<>();
        permissions1.add(permission1);
        role1.setPermissions(permissions1);
        Role role2 = new Role();
        role2.setRoleId(2);
        role2.setDescription("会员");
        role2.setRoleName("member");
        ArrayList<Permission> permissions2 = new ArrayList<>();
        permissions2.add(permission1);
        permissions2.add(permission2);
        role2.setPermissions(permissions2);

        //用户
        User user1 = new User();
        user1.setUsername("US.u1");
        user1.setRole(role1);
        User user2 = new User();
        user2.setUsername("US.u2");
        user2.setRole(role2);

        //图片
        Pic pic2 = new Pic();
        pic2.setPicUrl("123456.jpg");
        pic2.setUser(user1);
        Pic pic1 = new Pic();
        pic1.setPicUrl("123456.jpg");
        pic1.setUser(user2);
        ArrayList<Pic> pics1 = new ArrayList<>();
        pics1.add(pic1);
        ArrayList<Pic> pics2 = new ArrayList<>();
        pics2.add(pic2);
        //商品
        Item item1 = new Item();
        item1.setTitle("出售武器");
        item1.setServer("us");
        item1.setExchangeType("equipment");
        item1.setUser(user1);
        Item item2 = new Item();
        item2.setTitle("购买武器");
        item2.setServer("us");
        item2.setExchangeType("equipment");
        item2.setUser(user2);


        permissionRepository.save(permission1);
        permissionRepository.save(permission2);
        roleRepository.save(role1);
        roleRepository.save(role2);
        userRepository.save(user1);
        userRepository.save(user2);

        picRepository.save(pic1);
        picRepository.save(pic2);
        item2.setPics(picRepository.findAll());
        item1.setPics(picRepository.findAll());
        itemRepository.save(item1);
        itemRepository.save(item2);

    }

    /**
     * 大致测试
     */
    @Test
    @Transactional
    public void selectTest() {
        Item item = itemRepository.findById(6).orElse(new Item());
        System.out.println("item.name:"+item.getTitle());
        List<Pic> pics = item.getPics();
        System.out.print("pics:");
        for (Pic pic : pics) {
            System.out.print(pic.getPicUrl()+",");
        }
        System.out.println();
        User user = item.getUser();
        System.out.println("username:" + user.getUsername());
        Item item1 = user.getItems().get(0);
        System.out.println(item.getTitle());
    }
}
