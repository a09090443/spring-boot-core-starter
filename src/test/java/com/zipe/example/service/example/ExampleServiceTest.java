package com.zipe.example.service.example;

import com.zipe.example.base.TestBase;
import com.zipe.example.model.Game;
import com.zipe.example.service.ExampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/3/12 上午 09:56
 **/
public class ExampleServiceTest extends TestBase {

    @Autowired
    public ExampleService exampleServiceImpl;

    @Test
    public void findAllOfGameTest(){
        List<Game> games = exampleServiceImpl.findAllOfGames();
        System.out.println(games);
    }
}
