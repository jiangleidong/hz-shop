package com.qf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-16 22:43
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {
private int phone;
private String address;
private  String name;

private String orderno;
private Long id;

private  List<Pt> pts;




}
