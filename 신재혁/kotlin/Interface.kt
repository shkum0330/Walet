fun main(args: Array<String>){
    val car = Car2()
    car.drive()
    car.stop()
    car.destroy()

    val bike = Bike()
    bike.drive()
    bike.stop()
    bike.destroy()

    val bugatti = SuperCar()
    bugatti.drive()
    bugatti.stop()
    bugatti.showColor()
}

// 클래스는 하나만 상속받을 수 있지만 인터페이스는 여러개 받을 수 있다
interface Vehicle {
    fun drive()
    fun stop()
    fun destroy() = println("Vehicle is destroyed")
}
interface Color {
    fun showColor()
}
open class Car2 : Vehicle, Color {
    override fun drive() {
        println("Car is moving")
    }

    override fun stop() {
        println("Car is stopping")
    }

    override fun destroy(){
        super.destroy()
        println("The Vehicle was car")
    }

    override fun showColor() {
        println("Red color")
    }

}

class Bike : Vehicle {
    override fun drive() {
        println("Bike is moving")
    }

    override fun stop() {
        println("Bike is stopping")
    }

}

class SuperCar : Car2() {
    override fun drive() {
        println("Super Car is moving")
    }
//    override fun stop() {
//        println("Super Car is stopping")
//    }
    override fun showColor() {
        println("What color is your Bugatti")
    }

}