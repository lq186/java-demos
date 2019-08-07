package com.lq186.admin.util

import com.lq186.admin.common.EntityIdable
import com.lq186.admin.model.views.PageData
import org.springframework.data.domain.Page

final class PageUtils {

    def static toMap(Page<? extends EntityIdable> page) {
        def map = BeanUtils.toMap(page,
                ["number -> page",
                 "size",
                 "first",
                 "last",
                 "totalElements",
                 "totalPages"])

        map.put("page", map.get("page") + 1)

        def content = []
        if (page.content) {
            page.content.each {
                content.add(it.toMap())
            }
        }
        map.put("content", content)

        return map
    }

    static <V, E extends EntityIdable> PageData<V> toView(Page<E> page, Class<V> classOfV) {
        return new PageData<>(
                page: page.number,
                size: page.size,
                first: page.first,
                last: page.last,
                totalElements: page.totalElements,
                totalPages: page.totalPages,
                content: BeanUtils.viewFromEntity(page.content, classOfV)
        )
    }

}
