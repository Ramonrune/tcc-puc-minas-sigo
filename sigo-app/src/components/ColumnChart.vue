<template>
  <apexchart
    type="bar"
    :options="chartOptions"
    :series="series"
  ></apexchart>
</template>

<script>
export default {
  name: "AreaChart",
  data: function () {
    return {
      series: [
        {
          name: "Processos concluidos",
          data: [100, 120, 90, 110, 150, 80, 99, 120, 130],
        },
        {
          name: "Processos com inconformidade",
          data: [20, 15, 30, 40, 20, 10, 15, 10, 5],
        }
      ],
      chartOptions: {
        chart: {
          type: "bar",
          width: "100%",
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: "55%",
            endingShape: "rounded",
          },
        },
        dataLabels: {
          enabled: false,
        },
        stroke: {
          show: true,
          width: 2,
          colors: ["transparent"],
        },
        xaxis: {
          categories: [
            "Fev",
            "Mar",
            "Abr",
            "Mai",
            "Jun",
            "Jul",
            "Ago",
            "Set",
            "Out",
          ],
        },
        yaxis: {
          title: {
            text: "processos",
          },
        },
        fill: {
          opacity: 1,
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + " processos";
            },
          },
        },
      },
    };
  },
};
</script>
