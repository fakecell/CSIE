"use strict";
const common_vendor = require("../../../../common/vendor.js");
const _sfc_main = {
  name: "u-card",
  emits: ["click", "head-click", "body-click", "foot-click"],
  props: {
    full: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ""
    },
    titleColor: {
      type: String,
      default: "#303133"
    },
    titleSize: {
      type: [Number, String],
      default: "30"
    },
    subTitle: {
      type: String,
      default: ""
    },
    subTitleColor: {
      type: String,
      default: "#909399"
    },
    subTitleSize: {
      type: [Number, String],
      default: "26"
    },
    border: {
      type: Boolean,
      default: true
    },
    index: {
      type: [Number, String, Object],
      default: ""
    },
    margin: {
      type: String,
      default: "30rpx"
    },
    borderRadius: {
      type: [Number, String],
      default: "16"
    },
    headStyle: {
      type: Object,
      default() {
        return {};
      }
    },
    bodyStyle: {
      type: Object,
      default() {
        return {};
      }
    },
    footStyle: {
      type: Object,
      default() {
        return {};
      }
    },
    headBorderBottom: {
      type: Boolean,
      default: true
    },
    footBorderTop: {
      type: Boolean,
      default: true
    },
    thumb: {
      type: String,
      default: ""
    },
    thumbWidth: {
      type: [String, Number],
      default: "60"
    },
    thumbCircle: {
      type: Boolean,
      default: false
    },
    padding: {
      type: [String, Number],
      default: "30"
    },
    showHead: {
      type: Boolean,
      default: true
    },
    showFoot: {
      type: Boolean,
      default: true
    },
    boxShadow: {
      type: String,
      default: "none"
    }
  },
  data() {
    return {};
  },
  methods: {
    click() {
      this.$emit("click", this.index);
    },
    headClick() {
      this.$emit("head-click", this.index);
    },
    bodyClick() {
      this.$emit("body-click", this.index);
    },
    footClick() {
      this.$emit("foot-click", this.index);
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $props.showHead
  }, $props.showHead ? common_vendor.e({
    b: !_ctx.$slots.head
  }, !_ctx.$slots.head ? common_vendor.e({
    c: $props.title
  }, $props.title ? common_vendor.e({
    d: $props.thumb
  }, $props.thumb ? {
    e: $props.thumb,
    f: $props.thumbWidth + "rpx",
    g: $props.thumbWidth + "rpx",
    h: $props.thumbCircle ? "100rpx" : "6rpx"
  } : {}, {
    i: common_vendor.t($props.title),
    j: $props.titleSize + "rpx",
    k: $props.titleColor
  }) : {}, {
    l: $props.subTitle
  }, $props.subTitle ? {
    m: common_vendor.t($props.subTitle),
    n: $props.subTitleSize + "rpx",
    o: $props.subTitleColor
  } : {}) : {}, {
    p: common_vendor.s({
      padding: $props.padding + "rpx"
    }),
    q: common_vendor.s($props.headStyle),
    r: $props.headBorderBottom ? 1 : "",
    s: common_vendor.o((...args) => $options.headClick && $options.headClick(...args))
  }) : {}, {
    t: common_vendor.o((...args) => $options.bodyClick && $options.bodyClick(...args)),
    v: common_vendor.s({
      padding: $props.padding + "rpx"
    }),
    w: common_vendor.s($props.bodyStyle),
    x: $props.showFoot
  }, $props.showFoot ? {
    y: common_vendor.o((...args) => $options.footClick && $options.footClick(...args)),
    z: common_vendor.s({
      padding: _ctx.$slots.foot ? $props.padding + "rpx" : 0
    }),
    A: common_vendor.s($props.footStyle),
    B: $props.footBorderTop ? 1 : ""
  } : {}, {
    C: common_vendor.o((...args) => $options.click && $options.click(...args)),
    D: $props.border ? 1 : "",
    E: $props.full ? 1 : "",
    F: $props.borderRadius > 0 ? 1 : "",
    G: $props.borderRadius + "rpx",
    H: $props.margin,
    I: $props.boxShadow
  });
}
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__scopeId", "data-v-797f62c7"], ["__file", "D:/MyAllCoding/SmartCycling/mini-app/smartCycle-miniApp/uni_modules/vk-uview-ui/components/u-card/u-card.vue"]]);
wx.createComponent(Component);
