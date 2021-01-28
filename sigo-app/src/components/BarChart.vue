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
            width: '100%',
          },
          xaxis: {
            categories: [2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021],
          },
        },
        series: [
          {
            name: "Quantidade de processos",
            data: [1000, 1200, 1150, 1300, 1400, 1350, 1500, 1600],
          },
        ],
      },
    };
  },
};
</script>
