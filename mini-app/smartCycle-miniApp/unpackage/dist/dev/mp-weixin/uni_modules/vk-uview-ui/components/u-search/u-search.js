"use strict";
const common_vendor = require("../../../../common/vendor.js");
const _sfc_main = {
  name: "u-search",
  emits: ["update:modelValue", "input", "change", "search", "custom", "clear", "focus", "blur"],
  props: {
    value: {
      type: String,
      default: ""
    },
    modelValue: {
      type: String,
      default: ""
    },
    shape: {
      type: String,
      default: "round"
    },
    bgColor: {
      type: String,
      default: "#f2f2f2"
    },
    placeholder: {
      type: String,
      default: "\u8BF7\u8F93\u5165\u5173\u952E\u5B57"
    },
    clearabled: {
      type: Boolean,
      default: true
    },
    focus: {
      type: Boolean,
      default: false
    },
    showAction: {
      type: Boolean,
      default: true
    },
    actionStyle: {
      type: Object,
      default() {
        return {};
      }
    },
    actionText: {
      type: String,
      default: "\u641C\u7D22"
    },
    inputAlign: {
      type: String,
      default: "left"
    },
    disabled: {
      type: Boolean,
      default: false
    },
    animation: {
      type: Boolean,
      default: false
    },
    borderColor: {
      type: String,
      default: "none"
    },
    height: {
      type: [Number, String],
      default: 64
    },
    inputStyle: {
      type: Object,
      default() {
        return {};
      }
    },
    maxlength: {
      type: [Number, String],
      default: "-1"
    },
    searchIconColor: {
      type: String,
      default: ""
    },
    color: {
      type: String,
      default: "#606266"
    },
    placeholderColor: {
      type: String,
      default: "#909399"
    },
    margin: {
      type: String,
      default: "0"
    },
    searchIcon: {
      type: String,
      default: "search"
    }
  },
  data() {
    return {
      keyword: "",
      showClear: false,
      show: false,
      focused: this.focus
    };
  },
  watch: {
    keyword(nVal) {
      this.$emit("input", nVal);
      this.$emit("update:modelValue", nVal);
      this.$emit("change", nVal);
    },
    valueCom: {
      immediate: true,
      handler(nVal) {
        this.keyword = nVal;
      }
    }
  },
  computed: {
    valueCom() {
      return this.modelValue;
    },
    showActionBtn() {
      if (!this.animation && this.showAction)
        return true;
      else
        return false;
    },
    borderStyle() {
      if (this.borderColor)
        return `1px solid ${this.borderColor}`;
      else
        return "none";
    }
  },
  methods: {
    inputChange(e) {
      this.keyword = e.detail.value;
    },
    clear() {
      this.keyword = "";
      this.$nextTick(() => {
        this.$emit("clear");
      });
    },
    search(e) {
      this.$emit("search", e.detail.value);
      try {
        common_vendor.index.hideKeyboard();
      } catch (e2) {
      }
    },
    custom() {
      this.$emit("custom", this.keyword);
      try {
        common_vendor.index.hideKeyboard();
      } catch (e) {
      }
    },
    getFocus() {
      this.focused = true;
      if (this.animation && this.showAction)
        this.show = true;
      this.$emit("focus", this.keyword);
    },
    blur() {
      setTimeout(() => {
        this.focused = false;
      }, 100);
      this.show = false;
      this.$emit("blur", this.keyword);
    },
    clickHandler() {
      if (this.disabled)
        this.$emit("click");
    }
  }
};
if (!Array) {
  const _easycom_u_icon2 = common_vendor.resolveComponent("u-icon");
  _easycom_u_icon2();
}
const _easycom_u_icon = () => "../u-icon/u-icon.js";
if (!Math) {
  _easycom_u_icon();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: common_vendor.p({
      size: 30,
      name: $props.searchIcon,
      color: $props.searchIconColor ? $props.searchIconColor : $props.color
    }),
    b: common_vendor.o((...args) => $options.blur && $options.blur(...args)),
    c: $options.valueCom,
    d: common_vendor.o((...args) => $options.search && $options.search(...args)),
    e: common_vendor.o((...args) => $options.inputChange && $options.inputChange(...args)),
    f: $props.disabled,
    g: common_vendor.o((...args) => $options.getFocus && $options.getFocus(...args)),
    h: $props.focus,
    i: $props.maxlength,
    j: $props.placeholder,
    k: `color: ${$props.placeholderColor}`,
    l: common_vendor.s({
      textAlign: $props.inputAlign,
      color: $props.color,
      backgroundColor: $props.bgColor
    }),
    m: common_vendor.s($props.inputStyle),
    n: $data.keyword && $props.clearabled && $data.focused
  }, $data.keyword && $props.clearabled && $data.focused ? {
    o: common_vendor.p({
      name: "close-circle-fill",
      size: "34",
      color: "#c0c4cc"
    }),
    p: common_vendor.o((...args) => $options.clear && $options.clear(...args))
  } : {}, {
    q: $props.bgColor,
    r: $props.shape == "round" ? "100rpx" : "10rpx",
    s: $options.borderStyle,
    t: $props.height + "rpx",
    v: common_vendor.t($props.actionText),
    w: common_vendor.s($props.actionStyle),
    x: common_vendor.n($options.showActionBtn || $data.show ? "u-action-active" : ""),
    y: common_vendor.o((...args) => $options.custom && $options.custom(...args)),
    z: common_vendor.o((...args) => $options.clickHandler && $options.clickHandler(...args)),
    A: $props.margin
  });
}
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-3cb29fc1"], ["__file", "D:/MyAllCoding/SmartCycling/mini-app/smartCycle-miniApp/uni_modules/vk-uview-ui/components/u-search/u-search.vue"]]);
wx.createComponent(Component);
