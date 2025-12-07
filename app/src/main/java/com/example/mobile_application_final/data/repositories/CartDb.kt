package com.example.mobile_application_final.data.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobile_application_final.data.models.CartItem

class CartDb(context: Context): SQLiteOpenHelper(context, "local.db", null, 2) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE cart (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                itemId TEXT,
                quantity INTEGER
            )
        """.trimIndent())
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS cart")
        onCreate(db)
    }

    fun addCartItem(itemId: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("itemId", itemId)
            put("quantity", 1)
        }
        db.insert("cart", null, values)
    }

    fun getCartItem(id: String): CartItem {
        var item = CartItem(-1, "", 0)
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT id, itemId, quantity FROM cart WHERE itemId = ?",
            arrayOf(id)
        )
        if (cursor.moveToFirst()) {
            item = CartItem(
                cursor.getLong(0),    // id
                cursor.getString(1),  // itemId
                cursor.getLong(2)     // quantity
            )
        }
        cursor.close()
        return item
    }

    fun getCartItems(): List<CartItem> {
        val items = mutableListOf<CartItem>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM cart", null)
        while(cursor.moveToNext()) {
            items.add(
                CartItem(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                )
            )
        }
        cursor.close()
        return items
    }

    fun deleteCartItem(id: Long) {
        val db = writableDatabase
        db.delete("cart", "id = ?", arrayOf(id.toString()))
    }

    fun updateCartItem(id: Long, quantity: Long) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("quantity", quantity)
        }
        db.update("cart", values, "id = ?", arrayOf(id.toString()))
    }

}