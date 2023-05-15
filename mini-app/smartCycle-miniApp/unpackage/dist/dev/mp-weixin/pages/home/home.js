"use strict";
const common_vendor = require("../../common/vendor.js");
const store_index = require("../../store/index.js");
require("../../store/user.js");
const _sfc_main = {
  components: {},
  setup() {
    const title = common_vendor.ref("\u6211\u7684\u8F66\u8F86");
    const is_lock = common_vendor.ref(1);
    let cycles = common_vendor.ref([]);
    common_vendor.reactive({
      cname: "",
      ctype: "",
      error_message: ""
    });
    common_vendor.ref("");
    common_vendor.ref("");
    const cycle_unlock = (cycle) => {
      console.log("unlock");
      is_lock.value = 0;
      common_vendor.index.request({
        header: {
          "api-key": "5Hkq=bRIvmeoGzLN0Dp3zyqezsk="
        },
        url: "http://api.heclouds.com/cmds?device_id=1062127269",
        method: "POST",
        data: {
          "Cmd": 2
        },
        success: (resp) => {
          console.log(1);
          console.log(resp);
        }
      });
    };
    const cycle_lock = (cycle) => {
      is_lock.value = 1;
    };
    const refresh_cycles = () => {
      console.log(1);
      common_vendor.index.request({
        url: "http://localhost:3000/user/cycle/getList/",
        method: "GET",
        header: {
          Authorization: "Bearer " + store_index.store.state.user.token
        },
        success: (resp) => {
          cycles.value = resp.data;
        },
        fail: (resp) => {
          console.log(resp);
        }
      });
    };
    common_vendor.onTabItemTap(() => {
      refresh_cycles();
    });
    const running = (cycle) => {
      common_vendor.index.switchTab({
        url: "/pages/run/run"
      });
    };
    refresh_cycles();
    return {
      cycles,
      running,
      title,
      is_lock,
      cycle_unlock,
      cycle_lock
    };
  }
};
if (!Array) {
  const _easycom_u_button2 = common_vendor.resolveComponent("u-button");
  const _easycom_u_card2 = common_vendor.resolveComponent("u-card");
  (_easycom_u_button2 + _easycom_u_card2)();
}
const _easycom_u_button = () => "../../uni_modules/vk-uview-ui/components/u-button/u-button.js";
const _easycom_u_card = () => "../../uni_modules/vk-uview-ui/components/u-card/u-card.js";
if (!Math) {
  (_easycom_u_button + _easycom_u_card)();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.f($setup.cycles, (cycle, k0, i0) => {
      return common_vendor.e({
        a: common_vendor.t(cycle.ctype),
        b: common_vendor.t(cycle.cname),
        c: common_vendor.t(cycle.updatetime)
      }, $setup.is_lock === 1 ? {
        d: common_vendor.o(($event) => $setup.cycle_unlock(cycle), cycle.id),
        e: "07e72d3c-1-" + i0 + ",07e72d3c-0",
        f: common_vendor.p({
          type: "primary",
          size: "medium"
        })
      } : {
        g: common_vendor.o(($event) => $setup.cycle_lock(cycle), cycle.id),
        h: "07e72d3c-2-" + i0 + ",07e72d3c-0",
        i: common_vendor.p({
          type: "primary",
          size: "medium"
        })
      }, {
        j: common_vendor.o(($event) => $setup.running(cycle), cycle.id),
        k: "07e72d3c-3-" + i0 + ",07e72d3c-0",
        l: cycle.id
      });
    }),
    b: $setup.is_lock === 1,
    c: common_vendor.p({
      type: "primary",
      size: "medium"
    }),
    d: common_vendor.p({
      border: true
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-07e72d3c"], ["__file", "D:/MyAllCoding/SmartCycling/mini-app/smartCycle-miniApp/pages/home/home.vue"]]);
wx.createPage(MiniProgramPage);
