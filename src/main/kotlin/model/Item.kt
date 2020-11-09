package model

import java.time.LocalDateTime
import javax.print.DocFlavor

data class Item (var name:String, var value:Float, var billId:Int, var splits: MutableMap<String,Float>) {

    val id = LocalDateTime.now().hashCode()

    fun update(name:String, value:Float, recalc:Boolean, splits :MutableMap<String,Float>){
        this.name = name
        this.value = value
        if(recalc){
            this.recalc()
        }
        else{
            this.splits=splits
        }
    }

    fun recalc(){
        var splitRatio = mutableMapOf<String,Float>()
        for(x in splits){
            splitRatio[x.key]=x.value/this.value
        }
        //unsure if single loop would cause concurrent mod error
        for(x in splits){
            splits[x.key] = value * splitRatio[x.key]!!
        }
    }

}