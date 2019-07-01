package com.lq186.admin.util

import com.lq186.admin.entity.User
import org.junit.Test

class BeanUtilsTests {

    @Test
    void testToMap() {
        User user = new User(
                id: 1,
                dataId: "abcdefghi",
                username: "username_1",
                superUser: true,
                useState: 0,
                activeTime: System.currentTimeMillis(),
                invalidTime: System.currentTimeMillis()
        )
        def map = user.toMap()
        println map
    }

    @Test
    void testConvert() {
        println User.USE_STATES.contains(null)
    }
}
