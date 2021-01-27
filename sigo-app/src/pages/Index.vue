<template>
  <q-page>
    <div class="row">
      <div class="col-4">
        <bar-chart></bar-chart>
      </div>
      <div class="col-4">
        <area-chart></area-chart>
      </div>
    </div>
  </q-page>
</template>

<script>
import BarChart from "../components/BarChart";
import AreaChart from "../components/AreaChart";

export default {
  name: "PageIndex",
  components: { BarChart, AreaChart},
  data: function () {
    return {};
  },
};
</script>
