import kotlin.coroutines.experimental.buildSequence
import kotlin.math.sign

data class Point(val x: Float, val y: Float)

data class LineSegment(private val p0: Point, private val p1: Point) {
    constructor(x0: Float, y0: Float, x1: Float, y1: Float) : this(Point(x0, y0), Point(x1, y1))
    constructor(x0: Int, y0: Int, x1: Int, y1: Int) : this(x0.toFloat(), y0.toFloat(), x1.toFloat(), y1.toFloat())

    /**
     * Line segment crossing algorithm taken from https://stackoverflow.com/a/385873
     *
     * each line is represented using this formula:
     * a(x-xm) + b(y-ym) = c
     *
     * where xm and ym denote the median point like xm = (x0+x1)/2 and ym = (y0+y1)/2
     */

    private val xm: Float = (p0.x + p1.x) / 2f
    private val ym: Float = (p0.y + p1.y) / 2f
    private val a: Float = p1.y - p0.y
    private val b: Float = p0.x - p1.x

    private fun c(p: Point): Float = a * (p.x - xm) + b * (p.y - ym)

    private fun bothPointsOnDifferentSide(l: LineSegment): Boolean {
        val c1 = c(l.p0)
        val c2 = c(l.p1)
        return c1.sign != c2.sign
    }

    fun crosses(other: LineSegment) = bothPointsOnDifferentSide(other) && other.bothPointsOnDifferentSide(this)

}

data class Crossing(val lines: Set<LineSegment>)

fun isThisATriangle(c1: Crossing, c2: Crossing, c3: Crossing): Boolean {
    val connectingLines = mutableSetOf<LineSegment>()
    connectingLines.addAll(c1.lines.intersect(c2.lines))
    if (connectingLines.size < 1) return false
    connectingLines.addAll(c2.lines.intersect(c3.lines))
    if (connectingLines.size < 2) return false
    connectingLines.addAll(c3.lines.intersect(c1.lines))
    return connectingLines.size == 3
}

fun findAllCrossings(vararg lines: LineSegment): Set<Crossing> {
    if (lines.size < 2)
        return emptySet()
    val result = mutableSetOf<Crossing>()

    for (pair in permutations(lines, 2).map { it }) {
        if (pair[0].crosses(pair[1])) {
            result.add(Crossing(pair.toSet()))
        }
    }
    return result
}

/**
 * A sequence going through all possible permutations of n elements into sets of m elements
 */
fun <T> permutations(elements: Array<T>, m: Int): Sequence<List<T>> = buildSequence {
    for ((i, a) in elements.withIndex()) {
        if (m == 1)
            yield(listOf(a))
        else {
            for (next: List<T> in permutations(elements.copyOfRange(i + 1, elements.size), m - 1)) {
                yield(next + a)
            }
        }
    }
}

fun countTriangles(input: Set<Crossing>): Int {
    val triangles = mutableSetOf<Set<Crossing>>()
    for (potentialTriangle in permutations(input.toTypedArray(), 3)) {
        if (isThisATriangle(potentialTriangle)) {
            triangles.add(potentialTriangle.toSet())
        }
    }
    return triangles.size
}

fun countTriangles(vararg lines: LineSegment) = countTriangles(findAllCrossings(*lines))

fun isThisATriangle(crossings: Collection<Crossing>): Boolean {
    val cs = crossings.toList()
    return isThisATriangle(cs[0], cs[1], cs[2])
}


fun main(args: Array<String>) {
    val input = arrayOf(
            LineSegment(0, 0, 0, 200),
            LineSegment(0, 0, 120, 0),
            LineSegment(120, 0, 0, 200),
            LineSegment(0, 20, 100, 180),
            LineSegment(20, 0, 50, 180),
            LineSegment(100, 0, 50, 180)
    )

    val crossings = findAllCrossings(*input)
    println("There are ${crossings.size} crossing points in total")
    val countTriangles = countTriangles(crossings)
    println("There are $countTriangles unique triangles...")
}