package com.lq186.admin.util

import com.lq186.admin.common.EntityIdable
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

}
