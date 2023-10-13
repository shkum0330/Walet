fun main(args: Array<String>){
    val car = Car ("Red", "Volvo", 10)
    val car2 = Car ("Blue", "Tesla")
    println("Color of the car : ${car.color}")
}
class Car(val color : String, val name : String, val age : Int){
    init {
        println("Color : $color, " +
                "Name : $name, " +
                "Age : $age")
    }

    constructor(color : String, name : String) : this(color, name, 12 ){
        println("Secondary constructor")
    }
}