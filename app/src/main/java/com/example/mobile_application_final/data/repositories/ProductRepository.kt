package com.example.mobile_application_final.data.repositories

import com.example.mobile_application_final.R
import com.example.mobile_application_final.data.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("products")

    suspend fun getProducts(): List<Product> {
        return productsRef.get().await().documents.mapNotNull { it.toObject(Product::class.java) }
    }

    fun seedDb(){
        items.forEach { item ->
            db.collection("products")
                .add(item)
        }
    }

    fun Product.toMap(): Map<String, Any> {
        return mapOf(
            "category" to category,
            "description" to description,
            "image" to image,
            "name" to name,
            "price" to price,
            "rating" to rating,
            "stock" to stock
        )
    }

    val items = listOf(
        // -------------------- Pokémon --------------------
        Product(
            category = "PokeMon",
            description = "A shiny Pikachu sticker with electric spark effects.",
            image = R.drawable.shiny_pikachu,
            name = "Shiny Pikachu Spark Sticker",
            price = 250.0,
            rating = 5.0,
            stock = 14
        ),
        Product(
            category = "PokeMon",
            description = "A cute Charmander sticker breathing tiny cartoon fire.",
            image = R.drawable.cute_charmander,
            name = "Baby Charmander Flame Sticker",
            price = 180.0,
            rating = 4.0,
            stock = 22
        ),
        Product(
            category = "PokeMon",
            description = "A powerful Gengar sticker with neon purple vibes.",
            image = R.drawable.powerful_gengar,
            name = "Gengar Shadow Glow Sticker",
            price = 300.0,
            rating = 5.0,
            stock = 9
        ),
        Product(
            category = "PokeMon",
            description = "A Mew sticker floating with sparkles and pastel colors.",
            image = R.drawable.sparkle_mewtwo,
            name = "Pastel Mew Float Sticker",
            price = 220.0,
            rating = 4.0,
            stock = 30
        ),

        // -------------------- MTG --------------------
        Product(
            category = "MTG",
            description = "A Black Lotus–themed sticker with fantasy bloom effects.",
            image = R.drawable.black_lotus,
            name = "Arcane Black Lotus Sticker",
            price = 400.0,
            rating = 5.0,
            stock = 6
        ),
        Product(
            category = "MTG",
            description = "A Planeswalker silhouette sticker with fiery runes.",
            image = R.drawable.planeswalker_silhouette,
            name = "Flameforged Planeswalker Sticker",
            price = 180.0,
            rating = 4.0,
            stock = 19
        ),
        Product(
            category = "MTG",
            description = "An angel token sticker with glowing white wings.",
            image = R.drawable.angel_token,
            name = "Angel of Radiance Sticker",
            price = 230.0,
            rating = 5.0,
            stock = 17
        ),
        Product(
            category = "MTG",
            description = "A sticker of a snarling power-up dragon in MTG style.",
            image = R.drawable.snarlying_dragon,
            name = "Elder Ruby Dragon Sticker",
            price = 260.0,
            rating = 3.0,
            stock = 12
        ),

        // -------------------- D&D --------------------
        Product(
            category = "D&D",
            description = "A cartoon mimic chest sticker with goofy teeth.",
            image = R.drawable.cartoon_mimic_chest,
            name = "Friendly Mimic Sticker",
            price = 150.0,
            rating = 4.0,
            stock = 25
        ),
        Product(
            category = "D&D",
            description = "An adorable sticker of a tiny chibi Beholder.",
            image = R.drawable.tiny_adorable_chibi,
            name = "Chibi Beholder Sticker",
            price = 190.0,
            rating = 5.0,
            stock = 20
        ),
        Product(
            category = "D&D",
            description = "A cleric holy symbol with glowing divine light.",
            image = R.drawable.cleric_glowing_symbol,
            name = "Divine Light Holy Symbol Sticker",
            price = 160.0,
            rating = 4.0,
            stock = 18
        ),
        Product(
            category = "D&D",
            description = "A sticker of a d20 rolling a natural 20 in flames.",
            image = R.drawable.d20_rolling_fire,
            name = "Nat 20 Fire Dice Sticker",
            price = 200.0,
            rating = 5.0,
            stock = 35
        ),

        // -------------------- Anime --------------------
        Product(
            category = "Anime",
            description = "A chibi-style magical girl with sparkles and a wand.",
            image = R.drawable.tiny_adorable_chibi,
            name = "Magical Chibi Star Witch Sticker",
            price = 170.0,
            rating = 4.0,
            stock = 28
        ),
        Product(
            category = "Anime",
            description = "A samurai silhouette sticker with a red moon behind.",
            image = R.drawable.samurai_red_moon,
            name = "Red Moon Samurai Sticker",
            price = 240.0,
            rating = 5.0,
            stock = 16
        ),
        Product(
            category = "Anime",
            description = "A cat-girl mascot holding a giant bubble tea cup.",
            image = R.drawable.cat_girl_mascot,
            name = "Boba Catgirl Mascot Sticker",
            price = 150.0,
            rating = 4.0,
            stock = 40
        ),
        Product(
            category = "Anime",
            description = "A cool shonen hero power-up sticker with lightning.",
            image = R.drawable.hero_powerup,
            name = "Shonen Lightning Hero Sticker",
            price = 260.0,
            rating = 5.0,
            stock = 11
        )
    )
}