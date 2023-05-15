"use strict";
const common_vendor = require("../../common/vendor.js");
const store_index = require("../../store/index.js");
require("../../store/user.js");
const _sfc_main = {
  components: {},
  setup() {
    let username = common_vendor.ref("sunzhengqing");
    let password = common_vendor.ref("123");
    let error_message = common_vendor.ref("");
    let jwt_token = common_vendor.ref("");
    try {
      jwt_token = common_vendor.index.getStorageSync("jwt_token");
    } catch (e) {
    }
    console.log("jwt_token: " + jwt_token);
    if (jwt_token) {
      store_index.store.commit("updateToken", jwt_token);
      store_index.store.dispatch("getinfo", {
        success() {
          console.log("pushToHome");
          store_index.store.commit("updatePullingInfo", false);
        },
        error() {
          store_index.store.commit("updatePullingInfo", false);
        }
      });
    } else {
      store_index.store.commit("updatePullingInfo", false);
    }
    const login = () => {
      store_index.store.dispatch("login", {
        username: username.value,
        password: password.value,
        success() {
          store_index.store.dispatch("getinfo", {
            success() {
              console.log(store_index.store.state.user.username);
              console.log("login sussess");
              console.log("pushToHome");
            },
            error() {
              console.log(store_index.store.state.user.username);
            }
          });
        },
        error() {
          error_message.value = "\u7528\u6237\u540D\u6216\u5BC6\u7801\u9519\u8BEF";
        }
      });
    };
    login();
    return {
      username,
      password,
      login
    };
  }
};
if (!Array) {
  const _easycom_u_input2 = common_vendor.resolveComponent("u-input");
  const _easycom_u_button2 = common_vendor.resolveComponent("u-button");
  (_easycom_u_input2 + _easycom_u_button2)();
}
const _easycom_u_input = () => "../../uni_modules/vk-uview-ui/components/u-input/u-input.js";
const _easycom_u_button = () => "../../uni_modules/vk-uview-ui/components/u-button/u-button.js";
if (!Math) {
  (_easycom_u_input + _easycom_u_button)();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.o(($event) => $setup.username = $event),
    b: common_vendor.p({
      modelValue: $setup.username
    }),
    c: common_vendor.o(($event) => $setup.password = $event),
    d: common_vendor.p({
      modelValue: $setup.password
    }),
    e: common_vendor.o($setup.login),
    f: common_vendor.p({
      type: "info"
    })
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/MyAllCoding/SmartCycling/mini-app/smartCycle-miniApp/pages/my/my.vue"]]);
wx.createPage(MiniProgramPage);
