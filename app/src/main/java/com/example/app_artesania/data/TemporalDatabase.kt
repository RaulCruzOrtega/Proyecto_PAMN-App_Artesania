package com.example.app_artesania.data

import com.example.app_artesania.model.Product

class TemporalDatabase{
    companion object {
        fun getProduct(productId: String): Product? {
            for(product in products){
                if(productId == product.id){
                    return product
                }
            }
            return null
        }

        val products = ArrayList(
            listOf(
                Product.p1, Product.p2, Product.p3, Product.p4, Product.p5, Product.p6
            )
        )
    }
}