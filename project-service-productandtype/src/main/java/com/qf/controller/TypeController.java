package com.qf.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.qf.dto.ProductTypeDTO;
import com.qf.service.TypeService;


import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

import java.util.List;


/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-13 18:02
 **/
@Controller
@RequestMapping("type")
public class TypeController {


    @Autowired
    private TypeService typeService;

    @RequestMapping("gettype")
    @ResponseBody
    public List<ProductTypeDTO> gettype(Long pid){

        List<ProductTypeDTO> mapper = typeService.getMapper(pid);
        System.out.println(mapper);
        return mapper;

    }

    @RequestMapping("getalltype")
    @ResponseBody
    public R alltype(){
        JsonObject json=new JsonObject();



        Object s = (Object)get(0);


        System.out.println(s);

        return R.ok(s);
    }


    public String get(long id) {



        StringBuilder a=new StringBuilder();
        List<ProductTypeDTO> mapper = typeService.getMapper(id);

        if(mapper.size()!=0){

            Boolean A=Boolean.TRUE;
            a.append("{");
            List<String> list=new ArrayList<>();
            for (ProductTypeDTO ductTypeDTO : mapper) {



                List<ProductTypeDTO> type = typeService.getMapper(ductTypeDTO.getCid());

                if(type.size()!=0){
                   A=Boolean.FALSE;
                   a.append(ductTypeDTO.getCname()).append("=");
                        String s = get(ductTypeDTO.getCid());
                        a.append(s);

                }

              if(A){
                  a.deleteCharAt(a.length()-1);
                  a.append("[");
                  a.append(ductTypeDTO.getCname());

              }

                a.append(",");

            }
           a.deleteCharAt(a.length()-1);

            a.append("}");
            if(A){
                a.deleteCharAt(a.length()-1);
                a.append("]");


            }
        }


       return a.toString();
    }
}
