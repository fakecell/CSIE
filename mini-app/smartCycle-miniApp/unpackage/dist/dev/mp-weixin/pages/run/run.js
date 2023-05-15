"use strict";
const common_vendor = require("../../common/vendor.js");
require("../../common/qqmap-wx-jssdk.js");
const store_index = require("../../store/index.js");
require("../../store/user.js");
const _sfc_main = {
  components: {},
  setup() {
    let latitude = common_vendor.ref(41.856094);
    let longitude = common_vendor.ref(123.786289);
    let polyline_copy = [];
    let polyline = common_vendor.ref([]);
    let points = [];
    let destination = common_vendor.ref("");
    let show = common_vendor.ref(false);
    const dest_points = {
      "\u56DB\u6559": {
        "lat": "41.857686",
        "lng": "123.790119"
      }
    };
    const falsh = () => {
      common_vendor.index.switchTab({
        url: "/pages/home/home"
      });
    };
    const draw_polyline = (slat, slng, lat, lng) => {
      common_vendor.index.request({
        url: "https://apis.map.qq.com/ws/direction/v1/walking/?from=" + slat + "," + slng + "&to=" + lat + "," + lng + "&output=json&callback=cb&key=CKIBZ-A4OOD-N7P4S-P64VA-ZWUFE-L5FJD",
        method: "GET",
        success: (resp) => {
          console.log(resp);
          points = [];
          let steps = resp.data.result.routes[0].polyline;
          for (var i = 2; i < steps.length; i++) {
            steps[i] = steps[i - 2] + steps[i] / 1e6;
          }
          for (var i = 0; i < steps.length - 1; i += 2) {
            points.push({
              latitude: steps[i],
              longitude: steps[i + 1]
            });
          }
          polyline_copy.length = 0;
          polyline_copy.push({
            points,
            width: 3
          });
          console.log(polyline_copy);
          polyline.value = [];
          polyline.value = polyline_copy;
        }
      });
    };
    const run = () => {
      let dest = dest_points[destination.value];
      common_vendor.index.request({
        url: "http://localhost:3000/user/cycle/run/search",
        method: "POST",
        data: {
          "latitude": dest.lat,
          "longitude": dest.lng
        },
        header: {
          "Content-type": "application/x-www-form-urlencoded",
          Authorization: "Bearer " + store_index.store.state.user.token
        },
        success: (resp) => {
          console.log(resp.data.lng);
          draw_polyline(41.856131, 123.786253, resp.data.lat, resp.data.lng);
          getChange(resp);
        },
        fail: (resp) => {
          console.log(resp.data);
        }
      });
    };
    const getChange = (resp) => {
      common_vendor.index.startLocationUpdate({
        success: (res) => {
          common_vendor.index.onLocationChange(function(res2) {
            console.log(getDistance(res2.latitude, res2.longitude, resp.data.lat, resp.data.lng));
            if (getDistance(res2.latitude, res2.longitude, resp.data.lat, resp.data.lng) < 15) {
              console.log("\u60A8\u5230\u76EE\u7684\u5730\u4E86");
              show.value = true;
              draw_polyline(res2.latitude, res2.longitude, resp.data.lat, resp.data.lng);
              common_vendor.index.offLocationChange();
            }
            draw_polyline(res2.latitude, res2.longitude, resp.data.lat, resp.data.lng);
          });
        }
      });
    };
    const getDistance = (lat1, lng1, lat2, lng2) => {
      lat1 = lat1 || 0;
      lng1 = lng1 || 0;
      lat2 = lat2 || 0;
      lng2 = lng2 || 0;
      var rad1 = lat1 * Math.PI / 180;
      var rad2 = lat2 * Math.PI / 180;
      var a = rad1 - rad2;
      var b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
      var r = 6378137;
      var distance = r * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(rad1) * Math.cos(rad2) * Math.pow(Math.sin(b / 2), 2)));
      return distance;
    };
    return {
      latitude,
      longitude,
      polyline,
      destination,
      run,
      show,
      falsh
    };
  }
};
if (!Array) {
  const _easycom_u_search2 = common_vendor.resolveComponent("u-search");
  const _easycom_u_button2 = common_vendor.resolveComponent("u-button");
  const _easycom_u_popup2 = common_vendor.resolveComponent("u-popup");
  (_easycom_u_search2 + _easycom_u_button2 + _easycom_u_popup2)();
}
const _easycom_u_search = () => "../../uni_modules/vk-uview-ui/components/u-search/u-search.js";
const _easycom_u_button = () => "../../uni_modules/vk-uview-ui/components/u-button/u-button.js";
const _easycom_u_popup = () => "../../uni_modules/vk-uview-ui/components/u-popup/u-popup.js";
if (!Math) {
  (_easycom_u_search + _easycom_u_button + _easycom_u_popup)();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o($setup.run),
    b: common_vendor.o(($event) => $setup.destination = $event),
    c: common_vendor.p({
      placeholder: "\u8BF7\u8F93\u5165\u76EE\u7684\u5730",
      modelValue: $setup.destination
    }),
    d: common_vendor.o($setup.falsh),
    e: common_vendor.p({
      type: "primary",
      ripple: true,
      size: "medium"
    }),
    f: common_vendor.o(($event) => $setup.show = $event),
    g: common_vendor.p({
      mode: "bottom",
      width: "100%",
      height: "30%",
      closeable: true,
      ["close-icon-pos"]: "top-right",
      modelValue: $setup.show
    }),
    h: $setup.latitude,
    i: $setup.longitude,
    j: $setup.polyline
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/MyAllCoding/SmartCycling/mini-app/smartCycle-miniApp/pages/run/run.vue"]]);
wx.createPage(MiniProgramPage);
