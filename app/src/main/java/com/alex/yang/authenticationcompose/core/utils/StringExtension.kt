package com.alex.yang.authenticationcompose.core.utils

/**
 * Created by AlexYang on 2025/12/22.
 *
 *
 */


/* 規則：
* - 先 trim，再用空白切分（多空白視為一個分隔）
* - 取前 2 個 token，各取第一個字元
* - uppercase（對英文有效；非英文字元會保持原樣）
* - 若結果為空，回傳 fallback
*/
fun String?.toAvatarInitials(
    maxParts: Int = 2,
    fallback: String = "?"
): String {
    val raw = this?.trim().orEmpty()
    if (raw.isBlank()) return fallback

    val initials = raw
        .split(Regex("\\s+"))
        .asSequence()
        .filter { it.isNotBlank() }
        .take(maxParts)
        .map { it.take(1) }
        .joinToString("")
        .uppercase()

    return initials.ifBlank { fallback }
}