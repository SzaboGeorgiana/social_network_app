package com.example.lab6_fx_15_11.repo.paging;


import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Paginator<E> {
    private Pageable pageable;
    private Iterable<E> elements;

    public Paginator(Pageable pageable, Iterable<E> elements) {
        this.pageable = pageable;
        this.elements = elements;
    }

    public Page<E> paginate() {
        Stream<E> result = StreamSupport.stream(elements.spliterator(), false)
                .skip(pageable.getPageNumber()  * pageable.getPageSize())
                .limit(pageable.getPageSize());
        return new PageImplementation<>(pageable, result);
    }
}
