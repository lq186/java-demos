package com.lq186.admin.util

final class RandomUtils {

    private static final String ALL_CHARS_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

    private static final int ALL_CHARS_LENGTH = ALL_CHARS_STRING.length()

    private static final Random random = new Random(System.currentTimeMillis())

    static String randomChars(int length) {
        StringBuffer sb = new StringBuffer()
        for (i in 1..length) {
            sb.append(ALL_CHARS_STRING.charAt(random.nextInt(ALL_CHARS_LENGTH)))
        }
        return sb.toString()
    }

    static String uuid() {
        return UUID.randomUUID().toString().replace("-", "")
    }
}
