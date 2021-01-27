<template>
    <apexchart
      id="barChart"
      type="bar"
      :options="barChart.options"
      :series="barChart.series"
    ></apexchart>
</template>

<script>
export default {
  name: "BarChart",
  data: function () {
    return {
      barChart: {
        options: {
          chart: {
            id: "barChart",
            width: '100%'
          },
          xaxis: {
            categories: [2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021],
          },
        },
        series: [
          {
            name: "Faturamento (milhões de reais)",
            data: [30, 40, 45, 50, 49, 60, 70, 91],
          },
        ],
      },
    };
  },
};
</script>
