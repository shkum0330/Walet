fun main(args: Array<String>){
    val a = A()
    val b = B()
    val c = C()
    val d = D()
    println("a.a : ${a.a}")
    println("a.a : ${a.b}")
//    println("b.c : ${b.c}")
    println("b.d : ${b.d}")
//    println("c.e : ${c.e}")
//    println("c.f : ${c.f}")
    println("c.g : ${c.g}")
    d.printCProperties()
}
public class A {
    public val a = 1
    val b = 2
}
class B {
    private val c = 3
    public val d = 4
}
open class C {
    protected val e = 5
    private val f = 6
    public val g = 7
}

class D : C(){
    fun printCProperties(){
        println("e : $e")
//        println("f : $f")
        println("g : $g")
    }

}