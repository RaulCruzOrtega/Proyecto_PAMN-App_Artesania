package com.example.app_artesania.model

import com.example.app_artesania.R

enum class CategoryType{
    Cestería,
    Alfarería,
    Joyería,
    Cuchillería,
    Vestimenta,
    Zapatería,
    Juguetes,
    Comida,
    Otro
}

sealed class Category(
    val categoryType: CategoryType,
    val image: Int
) {
    object Cestería: Category(
        categoryType = CategoryType.Cestería,
        image = R.drawable.cesta
    )

    object Alfarería: Category(
        categoryType = CategoryType.Alfarería,
        image = R.drawable.ceramica
    )

    object Joyería: Category(
        categoryType = CategoryType.Joyería,
        image = R.drawable.joyas
    )

    object Vestimenta: Category(
        categoryType = CategoryType.Vestimenta,
        image = R.drawable.ropa
    )

    object Juguetes: Category(
        categoryType = CategoryType.Juguetes,
        image = R.drawable.juguete
    )

    object Cuchillería: Category(
        categoryType = CategoryType.Cuchillería,
        image = R.drawable.cuchillo
    )

    object Zapateria: Category(
        categoryType = CategoryType.Zapatería,
        image = R.drawable.zapato
    )

    object Comida: Category(
        categoryType = CategoryType.Comida,
        image = R.drawable.mermelada
    )

    object Otro: Category(
        categoryType = CategoryType.Otro,
        image = R.drawable.otros
    )

    fun getCategories(): ArrayList<Category> {
        return arrayListOf<Category>(
            Alfarería,
            Cestería,
            Joyería,
            Vestimenta,
            Juguetes,
            Cuchillería,
            Zapateria,
            Comida,
            Otro
        )
    }

    fun getCategory(category: String): Category{
        when (category) {
            "Alfarería" -> return Alfarería
            "Cestería" -> return Cestería
            "Joyería" -> return Joyería
            "Vestimenta" -> return Vestimenta
            "Juguetes" -> return Juguetes
            "Cuchillería" -> return Cuchillería
            "Zapateria" -> return Zapateria
            "Comida" -> return Comida
            else -> return Otro
        }
    }
}