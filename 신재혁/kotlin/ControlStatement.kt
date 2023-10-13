fun main(args: Array<String>){
    val numRange : IntRange = 1 .. 5
    println(numRange.contains(3))

    val charRange : CharRange = 'a' .. 'e'
    println(charRange.contains('z'))

    for(i in 1 .. 5){
        println(i)
    }

    for(i in 1 until 10){
        println(i)
    }

    for(i in 10 downTo 1){
        println(i)
    }

    for(i in 1 .. 10 step 2){
        println(i)
    }
    var num = 1
    while(num < 5){
        println("current number :  $num")
        num++
    }

    do {
        println("ahoy")
    } while(num < 5)

}