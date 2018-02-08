import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class TriangleTest {

    @Test
    fun testCrossingOfNothing() {
        assertEquals(findAllCrossings(), emptyList<LineSegment>())
    }

    @Test
    fun testCrossingOfOneLine() {
        assertEquals(findAllCrossings(LineSegment(0, 0, 10, 10)), emptyList<LineSegment>())
    }

    @Test
    fun testCrossingOfTwoLines() {
        val l1 = LineSegment(0, 0, 10, 10)
        val l2 = LineSegment(0, 0, 20, 30)
        assertEquals(findAllCrossings(l1, l2), setOf(Crossing(setOf(l1, l2))))
    }

    @Test
    fun testCrossingOfTwoLines2() {
        val l1 = LineSegment(0, 0, 10, 10)
        val l2 = LineSegment(0, 10, 10, 0)
        assertEquals(findAllCrossings(l1, l2), setOf(Crossing(setOf(l1, l2))))
    }

    @Test
    fun testCrossingOfTwoLines3() {
        val l1 = LineSegment(0, 0, 20, 30)
        val l2 = LineSegment(20, 30, 30, 0)
        assertEquals(findAllCrossings(l1, l2), setOf(Crossing(setOf(l1, l2))))
    }

    @Test
    fun testNoCrossingOfTwoLines() {
        val l1 = LineSegment(1, 1, 10, 10)
        val l2 = LineSegment(0, 0, 20, 30)
        assertEquals(findAllCrossings(l1, l2), emptySet<Crossing>())
    }

    @Test
    fun testNoCrossingOfTwoParallelLines() {
        val l1 = LineSegment(0, 0, 10, 10)
        val l2 = LineSegment(0, 1, 10, 11)
        assertEquals(findAllCrossings(l1, l2), emptySet<Crossing>())
    }

    @Test
    fun testCrossingOfThreeLines() {
        val l1 = LineSegment(0, 0, 10, 10)
        val l2 = LineSegment(0, 0, 20, 30)
        val l3 = LineSegment(20, 30, 30, 0)
        assertEquals(findAllCrossings(l1, l2, l3), setOf(Crossing(setOf(l1, l2)), Crossing(setOf(l2, l3))))
    }

    @Test
    fun testVerticalToHorizontalCrossing() {
        val l1 = LineSegment(0, 0, 0, 200)
        val l2 = LineSegment(0, 0, 120, 0)
        assertEquals(findAllCrossings(l1, l2), setOf(Crossing(setOf(l1, l2))))
    }

    @Test
    fun testPermutationsSingle() {
        assertEquals(permutations(arrayOf("A"), 1).toList(), listOf(listOf("A")))
    }

    @Test
    fun testPermutationsDoubleLengthOne() {
        assertEquals(permutations(arrayOf("A", "B"), 1).toSet(), setOf(listOf("A"), listOf("B")))
    }

    @Test
    fun testPermutationsDoubleLengthTwo() {
        assertEquals(permutations(arrayOf("A", "B"), 2).map { it.sorted() }.toSet(), setOf(listOf("A", "B")))
    }

    @Test
    fun testPermutationsThreeElementsLengthOne() {
        assertEquals(permutations(arrayOf("A", "B", "C"), 1).toSet(), setOf(listOf("A"), listOf("B"), listOf("C")))
    }

    @Test
    fun testPermutationsThreeElementsLengthTwo() {
        assertEquals(permutations(arrayOf("A", "B", "C"), 2).map { it.sorted() }.toSet(), setOf(listOf("A", "B"), listOf("A", "C"), listOf("B", "C")))
    }

    @Test
    fun testPermutationsThreeElementsLengthThree() {
        assertEquals(permutations(arrayOf("A", "B", "C"), 3).map { it.sorted() }.toSet(), setOf(listOf("A", "B", "C")))
    }

    @Test
    fun testPermutationsAmount3OutOf11() {
        assertEquals(permutations(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), 3).toList().size, 165)
    }

    @Test
    fun testPermutationsAmount4OutOf11() {
        assertEquals(permutations(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), 4).toList().size, 330)
    }

    @Test
    fun testPermutationsAmount10OutOf20() {
        assertEquals(permutations(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20), 10).toList().size, 184756)
    }

    @Test
    fun `example 1`() {
        val lines = extractLines("""
            |0.000000 0.000000 0.000000 200.000000
            |0.000000 0.000000 120.000000 0.000000
            |120.000000 0.000000 0.000000 200.000000
            |0.000000 20.000000 100.000000 180.000000
            |20.000000 0.000000 50.000000 180.000000
            |100.000000 0.000000 50.000000 180.000000
            """.trimMargin())

        assertEquals(countTriangles(*lines), 9)
    }

    @Test
    fun `example 2`() {
        val lines = extractLines("""
            |80.000000 120.000000 0.000000 70.000000
            |60.000000 190.000000 70.000000 80.000000
            |140.000000 140.000000 140.000000 160.000000
            |100.000000 110.000000 180.000000 70.000000
            |70.000000 80.000000 70.000000 100.000000
            |150.000000 70.000000 80.000000 20.000000
            |80.000000 20.000000 0.000000 140.000000
            """.trimMargin())

        assertEquals(countTriangles(*lines), 0)
    }

    @Test
    fun `example 3`() {
        val lines = extractLines("""
            |70.000000 120.000000 140.000000 40.000000
            |140.000000 110.000000 20.000000 10.000000
            |70.000000 0.000000 80.000000 100.000000
            |10.000000 20.000000 0.000000 70.000000
            |90.000000 120.000000 170.000000 90.000000
            |140.000000 40.000000 90.000000 160.000000
            |0.000000 80.000000 40.000000 90.000000
            |40.000000 190.000000 60.000000 150.000000
            |40.000000 90.000000 130.000000 50.000000
            |170.000000 90.000000 90.000000 170.000000
            """.trimMargin())

        assertEquals(countTriangles(*lines), 3)
    }

    @Test
    fun `example 4`() {
        val lines = extractLines("""
            |40.000000 160.000000 100.000000 10.000000
            |110.000000 110.000000 130.000000 0.000000
            |20.000000 170.000000 80.000000 80.000000
            |30.000000 150.000000 180.000000 10.000000
            |190.000000 70.000000 150.000000 50.000000
            |150.000000 50.000000 110.000000 130.000000
            |130.000000 0.000000 120.000000 170.000000
            |40.000000 100.000000 100.000000 70.000000
            |150.000000 70.000000 170.000000 160.000000
            |30.000000 100.000000 60.000000 10.000000
            |110.000000 130.000000 180.000000 120.000000
            |180.000000 120.000000 130.000000 160.000000
            |100.000000 130.000000 100.000000 150.000000
            """.trimMargin())

        assertEquals(countTriangles(*lines), 5)
    }

    @Test
    fun `example 5`() {
        val lines = extractLines("""
            |120.000000 0.000000 150.000000 180.000000
            |130.000000 130.000000 160.000000 140.000000
            |50.000000 150.000000 20.000000 140.000000
            |0.000000 170.000000 40.000000 20.000000
            |20.000000 140.000000 180.000000 0.000000
            |40.000000 20.000000 50.000000 80.000000
            |180.000000 0.000000 190.000000 40.000000
            |50.000000 80.000000 80.000000 140.000000
            |70.000000 190.000000 170.000000 30.000000
            |50.000000 80.000000 70.000000 60.000000
            |10.000000 190.000000 90.000000 180.000000
            |70.000000 60.000000 80.000000 80.000000
            |20.000000 120.000000 30.000000 110.000000
            |150.000000 90.000000 160.000000 80.000000
            |160.000000 140.000000 190.000000 0.000000
            |80.000000 140.000000 70.000000 70.000000
            |160.000000 140.000000 160.000000 170.000000
            |40.000000 20.000000 20.000000 30.000000
            """.trimMargin())

        assertEquals(countTriangles(*lines), 4)
    }

    private fun extractLines(input: String): Array<LineSegment> {
        return input.split("\n").map {
            val (x0, y0, x1, y1) = it.split(Regex("\\s+")).map { it.toFloat() }
            LineSegment(x0, y0, x1, y1)
        }.toTypedArray()
    }

}