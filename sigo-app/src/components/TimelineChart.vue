<template>
        <apexchart type="rangeBar" height="350" :options="chartOptions" :series="series"></apexchart>

</template>

<script>
import moment from "moment";

export default {
  name: "AreaChart",
  data: function () {
    return {
      series: [
        {
          data: [
            {
              x: "Insumos",
              y: [
                new Date("2019-02-27").getTime(),
                new Date("2019-03-03").getTime(),
              ],
              fillColor: "#008FFB",
            },
            {
              x: "Armazen.",
              y: [
                new Date("2019-03-04").getTime(),
                new Date("2019-03-08").getTime(),
              ],
              fillColor: "#00E396",
            },
            {
              x: "Fiação",
              y: [
                new Date("2019-03-07").getTime(),
                new Date("2019-03-11").getTime(),
              ],
              fillColor: "#775DD0",
            },
            {
              x: "Tecelagem",
              y: [
                new Date("2019-03-08").getTime(),
                new Date("2019-03-12").getTime(),
              ],
              fillColor: "#FEB019",
            },
            {
              x: "Entrega",
              y: [
                new Date("2019-03-12").getTime(),
                new Date("2019-03-17").getTime(),
              ],
              fillColor: "#FF4560",
            },
          ],
        },
      ],
      chartOptions: {
        chart: {
          width: '100%',
          type: "rangeBar",
        },
        plotOptions: {
          bar: {
            horizontal: true,
            distributed: true,
            dataLabels: {
              hideOverflowingLabels: false,
            },
          },
        },
        dataLabels: {
          enabled: true,
          formatter: function (val, opts) {
            var label = opts.w.globals.labels[opts.dataPointIndex];
            var a = moment(val[0]);
            var b = moment(val[1]);
            var diff = b.diff(a, "days");
            return label + ": " + diff + (diff > 1 ? "  dias" : " dia");
          },
          style: {
            colors: ["#f3f4f5", "#fff"],
          },
        },
        xaxis: {
          type: "datetime",
        },
        yaxis: {
          show: false,
        },
        grid: {
          row: {
            colors: ["#f3f4f5", "#fff"],
            opacity: 1,
          },
        },
      },
    };
  },
};
</script>
