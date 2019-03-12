package com.bwin.commons.beanutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teacher {

    private String id;
    private String name;
    private Integer age;
    private List<String> hobbies;

}
