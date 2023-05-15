"use strict";
const common_vendor = require("../common/vendor.js");
const ModelUser = {
  state: {
    id: "",
    username: "",
    photo: "",
    token: "",
    is_login: false,
    pulling_info: true
  },
  getters: {},
  mutations: {
    updateUser(state, user) {
      state.id = user.id;
      state.username = user.username;
      state.photo = user.photo;
      state.is_login = user.is_login;
    },
    updateToken(state, token) {
      state.token = token;
    },
    logout(state) {
      state.id = "";
      state.username = "";
      state.photo = "";
      state.token = "";
      state.is_login = false;
    },
    updatePullingInfo(state, pulling_info) {
      state.pulling_info = pulling_info;
    }
  },
  actions: {
    login(context, data) {
      common_vendor.index.request({
        header: {
          "Content-type": "application/x-www-form-urlencoded"
        },
        url: "http://localhost:3000/user/account/token/",
        method: "POST",
        data: {
          username: data.username,
          password: data.password
        },
        success: (resp) => {
          console.log(resp.data.error_massage);
          if (resp.data.error_massage === "success") {
            console.log(1);
            common_vendor.index.setStorageSync("jwt_token", resp.data.token);
            context.commit("updateToken", resp.data.token);
            data.success(resp);
          } else {
            data.error(resp);
          }
        },
        fail: (resp) => {
          data.error(resp);
        }
      });
    },
    getinfo(context, data) {
      common_vendor.index.request({
        url: "http://localhost:3000/user/account/info/",
        method: "GET",
        header: {
          Authorization: "Bearer " + context.state.token
        },
        success: (resp) => {
          if (resp.error_massage === "success") {
            console.log(resp);
            context.commit("updateUser", {
              ...resp,
              is_login: true
            });
            data.success(resp);
          } else {
            data.error(resp);
          }
        },
        fail: (resp) => {
          data.error(resp);
        }
      });
    },
    logout(context) {
      try {
        common_vendor.index.removeStorageSync("jwt_token");
        context.commit("logout");
      } catch (e) {
      }
    }
  },
  modules: {}
};
exports.ModelUser = ModelUser;
