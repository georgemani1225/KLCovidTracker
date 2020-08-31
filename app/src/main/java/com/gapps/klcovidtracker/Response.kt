package com.gapps.klcovidtracker

data class Response(
	val casesTimeSeries: List<CasesTimeSeriesItem?>? = null,
	val tested: List<TestedItem?>? = null,
	val statewise: List<StatewiseItem?>? = null
)

data class TestedItem(
	val totalsamplestested: String? = null,
	val source3: String? = null,
	val positivecasesfromsamplesreported: String? = null,
	val samplereportedtoday: String? = null,
	val source1: String? = null,
	val source: String? = null,
	val testsperconfirmedcase: String? = null,
	val individualstestedperconfirmedcase: String? = null,
	val testpositivityrate: String? = null,
	val testsconductedbyprivatelabs: String? = null,
	val testedasof: String? = null,
	val testspermillion: String? = null,
	val totalrtpcrtests: String? = null,
	val dailyrtpcrtests: String? = null,
	val updatetimestamp: String? = null,
	val totalindividualstested: String? = null,
	val totalpositivecases: String? = null
)

data class CasesTimeSeriesItem(
	val date: String? = null,
	val dailyrecovered: String? = null,
	val totalconfirmed: String? = null,
	val totaldeceased: String? = null,
	val dailydeceased: String? = null,
	val totalrecovered: String? = null,
	val dailyconfirmed: String? = null
)

data class StatewiseItem(
	val statenotes: String? = null,
	val recovered: String? = null,
	val deltadeaths: String? = null,
	val migratedother: String? = null,
	val deltarecovered: String? = null,
	val active: String? = null,
	val deltaconfirmed: String? = null,
	val state: String? = null,
	val statecode: String? = null,
	val confirmed: String? = null,
	val deaths: String? = null,
	val lastupdatedtime: String? = null
)

