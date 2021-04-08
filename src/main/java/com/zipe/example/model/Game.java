package com.zipe.example.model;

import com.zipe.base.model.Base;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Game extends Base {

    @Id
    private Long id;

    private String name;

    private String year;

    private Long price;

    private Long productId;

}
