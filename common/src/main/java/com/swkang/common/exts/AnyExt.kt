package com.swkang.common.exts

fun Any.getSuperClassNames(): String {
    var currentSuperClazz = this.javaClass.superclass
    if (!isAvailableClass(currentSuperClazz)) {
        return "ERROR_NO_SUPERCLASS"
    }
    val clazzNames = StringBuilder("")
    while (isAvailableClass(currentSuperClazz)) {
        clazzNames.append(currentSuperClazz.simpleName)
        currentSuperClazz = currentSuperClazz.superclass
        if (isAvailableClass(currentSuperClazz)) {
            clazzNames.append(".")
        }
    }
    return clazzNames.toString()
}

private fun isAvailableClass(clazz: Class<*>?): Boolean {
    return (clazz != null && !clazz.isInterface && "Object".notEqual(clazz.simpleName))
}