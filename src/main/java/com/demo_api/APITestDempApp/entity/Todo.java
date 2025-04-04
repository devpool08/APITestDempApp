package com.demo_api.APITestDempApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private int id;
    private String title;
    private String description;
    private boolean completed;
}
