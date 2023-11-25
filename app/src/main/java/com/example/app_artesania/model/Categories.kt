package com.example.app_artesania.model

import com.example.app_artesania.R

enum class TypeCategories{
    Cestería,
    Alfarería,
    Joyería,
    Cuchillería,
    Vestimenta,
    Zapatería,
    Juguetes
}

sealed class Categories(
    val categorie: TypeCategories,
    val image: Int
) {
    object Cestería: Categories(
        categorie = TypeCategories.Cestería,
        image = R.drawable.cesta
    )

    object Alfarería: Categories(
        categorie = TypeCategories.Alfarería,
        image = R.drawable.ceramica
    )

    object Joyería: Categories(
        categorie = TypeCategories.Joyería,
        image = R.drawable.joyas
    )

    object Vestimenta: Categories(
        categorie = TypeCategories.Vestimenta,
        image = R.drawable.ropa
    )

    object Juguetes: Categories(
        categorie = TypeCategories.Juguetes,
        image = R.drawable.juguete
    )

    object Cuchillería: Categories(
        categorie = TypeCategories.Cuchillería,
        image = R.drawable.cuchillo
    )

    object Zapateria: Categories(
        categorie = TypeCategories.Zapatería,
        image = R.drawable.zapato
    )
}