package com.zipe.example.service.impl;

import com.zipe.base.annotation.DS;
import com.zipe.example.jdbc.ExampleJDBC;
import com.zipe.example.model.Game;
import com.zipe.example.repository.GameRepository;
import com.zipe.example.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/3/12 上午 09:51
 **/
@Slf4j
@Service
public class ExampleServiceImpl implements ExampleService {
    private final String SQL_FILE_DIR_KEY = "SQL_EXAMPLE";

    private final ExampleJDBC jdbc;

    private final GameRepository gameRepository;

    @Autowired
    ExampleServiceImpl(GameRepository gameRepository, ExampleJDBC jdbc) {
        this.gameRepository = gameRepository;
        this.jdbc = jdbc;
    }

    @Override
    @DS("example")
    public List<Game> findAllOfGames() {
        return gameRepository.findAll();
    }
}
