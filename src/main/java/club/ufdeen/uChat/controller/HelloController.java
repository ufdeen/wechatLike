package club.ufdeen.uChat.controller;

import club.ufdeen.uChat.dao.UsersMapper;
import club.ufdeen.uChat.pojo.Users;
import club.ufdeen.uChat.pojo.UsersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 * User: uf.deen
 * Date: 2018-11-08
 */
@RestController
public class HelloController {
    @Autowired
    private UsersMapper usersMapper;

    @GetMapping("/hello")
    public String hello() {
        return "hello uchat";
    }

    @GetMapping("/testMybaties")
    public List<Users> testMybaties() {
        return usersMapper.selectByExample(new UsersExample());

    }
}
