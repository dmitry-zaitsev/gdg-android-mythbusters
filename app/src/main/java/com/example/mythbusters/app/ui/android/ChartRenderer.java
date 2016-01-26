package com.example.mythbusters.app.ui.android;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Manages rendering of the chart view
 */
public class ChartRenderer {

    private final BarChart chart;
    private final Formatter valueFormatter;

    private final String firstTitle;
    private final String secondTitle;

    public ChartRenderer(BarChart chart,
                         Formatter valueFormatter,
                         String firstTitle,
                         String secondTitle) {
        this.chart = chart;
        this.valueFormatter = valueFormatter;
        this.firstTitle = firstTitle;
        this.secondTitle = secondTitle;

        setUpChart();
    }

    private void setUpChart() {
        chart.setTouchEnabled(false);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setDescription("");
        chart.setMaxVisibleValueCount(60);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setValueFormatter((value, yAxis) -> valueFormatter.format(value));
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend legend = chart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);
    }

    /**
     * Renders data.
     *
     * @param firstValue  value for the first bar
     * @param secondValue value for the second bar
     */
    public void renderValues(long firstValue, long secondValue) {
        chart.setData(
                buildBarData(firstValue, secondValue)
        );
        chart.invalidate();
    }

    private BarData buildBarData(long firstValue, long secondValue) {
        return new BarData(
                asList(
                        firstTitle,
                        secondTitle
                ),
                buildBarDataSets(firstValue, secondValue)
        );
    }

    private List<IBarDataSet> buildBarDataSets(long firstValue, long secondValue) {
        return asList(
                buildFirstDataSet(firstValue),
                buildSecondDataSet(secondValue)
        );
    }

    private IBarDataSet buildFirstDataSet(long firstValue) {
        final BarDataSet set = new BarDataSet(
                singletonList(
                        new BarEntry((float) firstValue, 0)
                ),
                firstTitle
        );
        set.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> valueFormatter.format(value));
        set.setColor(0xFF42A5F5);

        return set;
    }

    private IBarDataSet buildSecondDataSet(long secondValue) {
        final BarDataSet set = new BarDataSet(
                singletonList(
                        new BarEntry((float) secondValue, 1)
                ),
                secondTitle
        );
        set.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> valueFormatter.format(value));
        set.setColor(0xFFE57373);

        return set;
    }

    /**
     * Formats value
     */
    public interface Formatter {

        /**
         * @return {@link String} representation of the value
         */
        String format(float value);

    }

}
