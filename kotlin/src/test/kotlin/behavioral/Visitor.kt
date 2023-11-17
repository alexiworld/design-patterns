package behavioral

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

// Visitor
// - Separation between the algorithm and the objects they operate on
// - 2 concepts : visitor and element (visitable)
// - The elements accepts visitor type objects
// - Visitor performs operations on the elements

interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>): R
}

class FixedPriceContract(var costPerYear: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class TimeAndMaterialsContract(var costPerHour: Long, val hours: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class SupportContract(val costPerMonth: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

interface ReportVisitor<out R> {
    fun visit(contract: FixedPriceContract): R;
    fun visit(contract: TimeAndMaterialsContract): R;
    fun visit(contract: SupportContract): R;
}

class MonthlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear / 12

    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear

    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12
}

class VisitorTest {
    @Test
    fun testVisitor() {
        val projectAlpha = FixedPriceContract(10_000)
        val projectBeta = SupportContract(500)
        val projectGamma = TimeAndMaterialsContract(150, 10)
        val projectKappa = TimeAndMaterialsContract(50, 50)
        val project: ArrayList<ReportElement> = arrayListOf(projectAlpha, projectBeta, projectGamma, projectKappa)

        val monthlyCostReportVisitor = MonthlyCostReportVisitor()
        val monthlyCost : Long = project.map {it -> it.accept(monthlyCostReportVisitor)}.sum()
        println("Monthly cost: $monthlyCost")
        Assertions.assertThat(monthlyCost).isEqualTo(5333)

        val yearlyCostReportVisitor = YearlyCostReportVisitor()
        val annumCost : Long = project.map {it -> it.accept(yearlyCostReportVisitor)}.sum()
        println("Monthly cost: $annumCost")
        Assertions.assertThat(annumCost).isEqualTo(20_000)
    }

}