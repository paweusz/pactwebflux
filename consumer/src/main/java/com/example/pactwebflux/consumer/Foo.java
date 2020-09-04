package com.example.pactwebflux.consumer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class Foo {

    Long id;
    String name;

}
