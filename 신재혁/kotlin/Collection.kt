fun main(args: Array<String>){
    val fruitList = listOf("Banana", "Apple", "Melon")
    println("First Fruit : ${fruitList[0]}")

    val mutableFruitList = mutableListOf("Banana", "Apple", "Melon")
    println("First Fruit : ${mutableFruitList[0]}")
    mutableFruitList[0] = "Grape"
    println("First Fruit : ${mutableFruitList[0]}")

    mutableFruitList.forEach{fruit ->
        println("MyFruitList : $fruit")
    }

    val immutableNumSet = setOf(1,1,2,2,3,3,3,4,4,4,4);
    println(immutableNumSet)
    val mutableNumSet = mutableSetOf(1,1,2,2,3,4,4)

    mutableNumSet.add(500)
    mutableNumSet.remove(1)
    println(mutableNumSet)

    val immutableMap = mapOf("name" to "Man", "age" to 9999, "height" to 500)
    println(immutableMap["name"])
    println(immutableMap["no"])

    val mutableMap = mutableMapOf("name" to "Man", "age" to 9999, "height" to 500)

    mutableMap["age"] = 9


    mutableMap.put("hobby", "coding")
    mutableMap.remove("name")
    mutableMap.replace("height", 100)

    println(mutableMap)
}