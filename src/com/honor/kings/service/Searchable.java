package com.honor.kings.service;

import java.util.List;

public interface Searchable<T> {
    List<T> searchByName(String keyword);
}
