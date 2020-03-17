package com.qf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: hz-shop
 * @description: 封装的订单详情
 * @author: Mr.jiang
 * @create: 2020-03-16 22:59
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pt implements Serializable {
    int count;
    Long pid;
}
