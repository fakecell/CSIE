"use strict";
const common_vendor = require("../common/vendor.js");
const store_user = require("./user.js");
const store = common_vendor.createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    user: store_user.ModelUser
  }
});
exports.store = store;
