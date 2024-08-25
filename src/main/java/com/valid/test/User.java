package com.valid.test;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class User {
    @NotEmpty
    @Length(min = 6,max = 12)
    private String name;
}
