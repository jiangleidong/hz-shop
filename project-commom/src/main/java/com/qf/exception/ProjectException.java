package com.qf.exception;

import lombok.Getter;

/**
 * @program: hz-shop
 * @description: 自定义异常
 * @author: Mr.jiang
 * @create: 2020-03-09 22:49
 **/


import lombok.Getter;

    @Getter
    public class ProjectException extends RuntimeException {

        private Integer code;

        public ProjectException(Integer code, String message) {
            super(message);
            this.code = code;
        }
        public ProjectException() {
        }
    }