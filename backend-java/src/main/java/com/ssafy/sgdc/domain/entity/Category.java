package com.ssafy.sgdc.domain.entity;

import com.ssafy.sgdc.domain.entity.enums.CategoryName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "category_id")
    private long categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private CategoryName categoryName;
}
