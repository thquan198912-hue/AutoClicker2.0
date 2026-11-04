package com.example.autoclicker

import org.json.JSONArray
import org.json.JSONObject

/**
 * Một bước trong chuỗi: chạm tại (x1, y1) hoặc vuốt từ (x1, y1) đến (x2, y2).
 * [delayMs] là thời gian chờ trước khi thực hiện bước này,
 * [durationMs] là thời gian của cú vuốt (bỏ qua với chạm).
 */
class SeqStep(
    var swipe: Boolean,
    var x1: Float,
    var y1: Float,
    var x2: Float,
    var y2: Float,
    var delayMs: Long,
    var durationMs: Long
)

object SeqStore {

    fun toJson(steps: List<SeqStep>): String {
        val arr = JSONArray()
        for (s in steps) {
            arr.put(
                JSONObject()
                    .put("s", if (s.swipe) 1 else 0)
                    .put("x1", s.x1.toDouble())
                    .put("y1", s.y1.toDouble())
                    .put("x2", s.x2.toDouble())
                    .put("y2", s.y2.toDouble())
                    .put("d", s.delayMs)
                    .put("t", s.durationMs)
            )
        }
        return arr.toString()
    }

    fun fromJson(json: String): ArrayList<SeqStep> {
        val out = ArrayList<SeqStep>()
        if (json.isBlank()) return out
        try {
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                out.add(
                    SeqStep(
                        o.getInt("s") == 1,
                        o.getDouble("x1").toFloat(),
                        o.getDouble("y1").toFloat(),
                        o.getDouble("x2").toFloat(),
                        o.getDouble("y2").toFloat(),
                        o.getLong("d"),
                        o.getLong("t")
                    )
                )
            }
        } catch (e: Exception) {
            out.clear()
        }
        return out
    }
}
