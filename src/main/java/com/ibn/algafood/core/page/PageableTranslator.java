package com.ibn.algafood.core.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> values) {
        List<Sort.Order> orders = pageable.getSort().stream()
                .filter(order -> values.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), values.get(order.getProperty())))
                .toList();


        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(orders));
    }
}
