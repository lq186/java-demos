package com.lq186.admin.context

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

final class PageRequestContext {

    private static final ThreadLocal<PageRequest> PAGE_REQUEST_THREAD_LOCAL = new ThreadLocal<>()

    def static setPageRequest(int page, int size, String sort) {
        PageRequest pageRequest = new PageRequest(page, size, buildSort(sort))
        PAGE_REQUEST_THREAD_LOCAL.set(pageRequest)
    }

    static Pageable getPageRequest() {
        def pageRequest = PAGE_REQUEST_THREAD_LOCAL.get()
        if (!pageRequest) {
            return new PageRequest(0, 10)
        }
        return pageRequest
    }

    private static Sort buildSort(String sort) {

        if (!sort) {
            return Sort.unsorted()
        }

        def orders = []
        sort.split(",").each {
            def index = it.indexOf(":")
            def prop = it.substring(0, index)
            def direction = it.substring(index + 1)
            if ("desc".equalsIgnoreCase(direction)) {
                orders.add(Sort.Order.desc(prop))
            } else {
                orders.add(Sort.Order.asc(prop))
            }
        }
        if (orders) {
            return new Sort(orders)
        } else {
            return Sort.unsorted()
        }
    }

    static void clearPageRequest() {
        PAGE_REQUEST_THREAD_LOCAL.remove()
    }
}
