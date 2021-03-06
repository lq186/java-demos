package com.lq186.admin.util

import com.lq186.admin.common.EntityIdable

final class BeanUtils {

    static <V, E extends EntityIdable> V viewFromEntity(E entity, Class<V> classOfV) {
        V instance = classOfV.newInstance()
        def properties = instance.getProperties().keySet()
        def propertiesArray = []
        properties.each {
            it == "id" ? propertiesArray.add("dataId -> id") : propertiesArray.add(it)
        }
        copyPropertiesIfNoneNullAndNotEquals(entity, instance, propertiesArray)
        return instance
    }

    static <V, E extends EntityIdable> List<V> viewFromEntity(List<E> entities, Class<V> classOfV) {
        if (!entities) {
            return []
        }

        V instance = classOfV.newInstance()
        def properties = instance.getProperties().keySet()
        def propertiesArray = []
        properties.each {
            it == "id" ? propertiesArray.add("dataId -> id") : propertiesArray.add(it)
        }

        def array = []
        entities.each {
            instance = classOfV.newInstance()
            copyPropertiesIfNoneNullAndNotEquals(it, instance, propertiesArray)
            array.add(instance)
        }
        return array
    }

    static <E extends EntityIdable> E entityFromParam(Object param, Class<E> classOfE) {
        E instance = classOfE.newInstance()
        def properties = param.getProperties().keySet()
        def propertiesArray = []
        properties.each {
            it == "id" ? propertiesArray.add("id -> dataId") : propertiesArray.add(it)
        }
        copyPropertiesIfNoneNullAndNotEquals(param, instance, propertiesArray)
        return instance
    }

    def static toMap(def bean, List<String> properties) {
        def map = [:]
        addToMap(map, bean, properties)
        return map
    }

    def static addToMap(Map map, def bean, List<String> properties) {
        if (properties) {
            properties.each {
                def index = it.indexOf("->")
                if (index > 0) {
                    def prop = it.substring(0, index).trim()
                    def apiProp = it.substring(index + 2).trim()
                    map.put(apiProp, bean[prop])
                } else {
                    map.put(camelCaseToUnderscore(it), bean[it])
                }
            }
        }
        return map
    }

    def static copyPropertiesIfNoneNullAndNotEquals(def srcBean, def distBean, List<String> properties) {
        if (!properties || !srcBean || !distBean) {
            return
        }
        properties.each {
            if (srcBean[it] != null && srcBean[it] != distBean[it]) {
                distBean[it] = srcBean[it]
            }
        }
    }

    def static camelCaseToUnderscore(String propCamel) {
        // 如有缓存，则返回缓存值
        if (PROP_CACHE.containsKey(propCamel)) {
            return PROP_CACHE.get(propCamel)
        }
        def buffer = new StringBuffer()
        def propCamelCharArray = propCamel.toCharArray()
        propCamelCharArray.each {
            // it in 'A' - 'Z'
            // 如果遇到大写字母，则向前添加下划线，大写转小写
            if (65 <= it && it <= 90) {
                buffer.append("_").append((it + 32) as char)
            } else {
                buffer.append(it)
            }
        }
        def propUnderline = buffer.toString()
        PROP_CACHE.put(propCamel, propUnderline)
        return propUnderline
    }

    private static final Map<String, String> PROP_CACHE = new HashMap<>()
}
