package com.lq186.admin.service

import com.lq186.admin.entity.User
import com.lq186.admin.util.PageUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import javax.annotation.Resource

@RunWith(SpringRunner)
@SpringBootTest
class ServiceTests {

    @Resource
    UserService userService

    @Test
    void testInsertAndQuery() {
        def now = System.currentTimeMillis()
        User user = new User(
                dataId: uuid(),
                username: "ABC",
                displayName: "风云变幻",
                passwordMd5: uuid(),
                passwordSalt: "ASDFG",
                superUser: true,
                useState: 1,
                activeTime: now,
                invalidTime: now,
                createTime: now
        )
        userService.save(user)

        def page = userService.findPage("A", null)
        println PageUtils.toMap(page)

        page = userService.findPage(null, null)
        println PageUtils.toMap(page)

        page = userService.findPage("A", 2)
        println PageUtils.toMap(page)

        page = userService.findPage(null, 2)
        println PageUtils.toMap(page)
    }

    def static uuid() {
        UUID.randomUUID().toString().replace("-", "")
    }

}
