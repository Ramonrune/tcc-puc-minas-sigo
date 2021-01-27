<template>
    <apexchart
      type="pie"
      :options="chartOptions"
      :series="series"
    ></apexchart>
</template>

<script>
export default {
  name: "AreaChart",
  data: function () {
    return {
      series: [44, 10, 13, 43, 22],
      chartOptions: {
        chart: {
          width: '100%',
          type: "pie",
        },
        labels: ["Algodão", "Lã", "Linho", "Poliéster", "Outros"],
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                width: 200,
              },
              legend: {
                position: "bottom",
              },
            },
          },
        ],
      },
    };
  },
};
</script>
