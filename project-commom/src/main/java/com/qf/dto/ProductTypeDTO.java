package com.qf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: hz-shop
 * @description: 分类现实
 * @author: Mr.jiang
 * @create: 2020-03-13 17:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeDTO implements Serializable {

    private Long cid;

    private String cname;
}
