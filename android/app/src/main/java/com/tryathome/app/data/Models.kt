package com.tryathome.app.data

data class Category(
    val id: String,
    val name: String,
    val slug: String,
    val imageUrl: String,
    val status: String = "ACTIVE"
)

data class ProductImage(
    val id: String,
    val imageUrl: String,
    val isPrimary: Boolean = false,
    val caption: String = ""
)

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val description: String,
    val sellingPrice: Double, // Matches admin_selling_price/selling_price strictly. Cost prices are fully hidden!
    val mrp: Double,
    val discountPercentage: Int,
    val rating: Double,
    val ratingCount: Int,
    val images: List<ProductImage>,
    val sizes: List<String>,
    val colors: List<String>,
    val stock: Int,
    val categoryName: String,
    val categorySlug: String,
    val fabric: String = ""
)

data class CartItem(
    val id: String,
    val product: Product,
    val size: String,
    val color: String,
    var quantity: Int
)

object MockData {
    val categories = listOf(
        Category("cat-1", "Jeans", "jeans", "https://images.unsplash.com/photo-1542272604-787c3835535d?w=600&q=80"),
        Category("cat-2", "Shirts", "shirts", "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&q=80"),
        Category("cat-3", "Kurtis", "kurtis", "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=600&q=80"),
        Category("cat-4", "T-Shirts", "t-shirts", "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=600&q=80")
    )

    val products = listOf(
        Product(
            id = "p-1",
            name = "Premium Indigo Slim Fit Denim Jeans",
            brand = "Roadster",
            description = "Handcrafted pure cotton indigo jeans, perfect for everyday casual wear. Features durable stitching, premium washed finish, and classic five-pocket styling.",
            sellingPrice = 1299.0,
            mrp = 2499.0,
            discountPercentage = 48,
            rating = 4.4,
            ratingCount = 128,
            images = listOf(
                ProductImage("img-1a", "https://images.unsplash.com/photo-1542272604-787c3835535d?w=600&q=80", true, "Front View"),
                ProductImage("img-1b", "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=600&q=80", false, "Back View")
            ),
            sizes = listOf("S", "M", "L", "XL"),
            colors = listOf("Indigo Blue", "Sky Blue"),
            stock = 12,
            categoryName = "Jeans",
            categorySlug = "jeans",
            fabric = "100% Cotton Denim"
        ),
        Product(
            id = "p-2",
            name = "Pure Linen Mandarin Collar Shirt",
            brand = "Wrogn",
            description = "Lightweight and highly breathable pure linen shirt. Features a modern mandarin band collar, premium button-down front, and curved hemline.",
            sellingPrice = 1499.0,
            mrp = 2999.0,
            discountPercentage = 50,
            rating = 4.2,
            ratingCount = 94,
            images = listOf(
                ProductImage("img-2a", "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&q=80", true, "Front View")
            ),
            sizes = listOf("M", "L", "XL"),
            colors = listOf("White", "Linen Grey"),
            stock = 4,
            categoryName = "Shirts",
            categorySlug = "shirts",
            fabric = "100% Linen"
        ),
        Product(
            id = "p-3",
            name = "Lucknowi Chikankari Hand Embroidered Kurti",
            brand = "Anouk",
            description = "Traditional Lucknowi Chikankari hand embroidered kurti in pure georgette fabric. Adorned with beautiful floral motifs and intricate thread work.",
            sellingPrice = 1899.0,
            mrp = 3999.0,
            discountPercentage = 52,
            rating = 4.6,
            ratingCount = 215,
            images = listOf(
                ProductImage("img-3a", "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=600&q=80", true, "Front View")
            ),
            sizes = listOf("S", "M", "L", "XL", "XXL"),
            colors = listOf("Peach", "Mint Green"),
            stock = 15,
            categoryName = "Kurtis",
            categorySlug = "kurtis",
            fabric = "Georgette with Inner"
        ),
        Product(
            id = "p-4",
            name = "Oversized Streetwear Graphic Tee",
            brand = "HRX",
            description = "Heavyweight 240 GSM pre-shrunk cotton oversized t-shirt. Features a vibrant, high-density chest print and comfortable drop-shoulder silhouette.",
            sellingPrice = 799.0,
            mrp = 1499.0,
            discountPercentage = 46,
            rating = 4.1,
            ratingCount = 82,
            images = listOf(
                ProductImage("img-4a", "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=600&q=80", true, "Front View")
            ),
            sizes = listOf("S", "M", "L", "XL"),
            colors = listOf("Charcoal Black", "Vintage White"),
            stock = 8,
            categoryName = "T-Shirts",
            categorySlug = "t-shirts",
            fabric = "100% Premium Cotton"
        )
    )
}
